package cs.miu.edu;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
/**
 * @author Prabhat Gyawali
 * @project BigDataProject
 */
public class ImdbHBaseSqlApplication {

    public static void main(String[] args) throws AnalysisException, IOException {
        String tableName = args[0];
        SparkConf conf = new SparkConf().setAppName("imdb-hbase-sql").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession
                .builder()
                .appName("imdb-hbase-sql")
                .config(conf)
                .getOrCreate();

        readFromHbaseDBAndWriteToHDFSCsv(sc, spark, tableName);

        spark.stop();
        sc.close();
    }


    private static StructType getSchema(){
        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("seriesTitle", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("releasedYear", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("runtime", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("imdbRating", DataTypes.DoubleType, false));
        fields.add(DataTypes.createStructField("overview", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("genre", DataTypes.StringType, true));
        return DataTypes.createStructType(fields);
    }

    private static Row createRow(Movie movie){
        return  RowFactory.create(movie.getSeriesTitle(),
                movie.getReleasedYear(),
                movie.getRuntime(),
                movie.getImdbRating(),
                movie.getOverview(),
                movie.getGenre());
    }

    private static void readFromHbaseDBAndWriteToHDFSCsv(JavaSparkContext sc, SparkSession spark, String tableName) throws IOException {

        JavaRDD<Movie> movieJavaRDD = sc.parallelize(new HBaseScanner(tableName).fetchMoviesFromHBase());
        JavaRDD<Row> rowRDD = movieJavaRDD.map(ImdbHBaseSqlApplication::createRow);

        Dataset<Row> dataFrame = spark.createDataFrame(rowRDD, getSchema());
        dataFrame.createOrReplaceTempView(tableName);

        Dataset<Row> allMovies = spark.sql("SELECT seriesTitle, releasedYear, runtime,imdbRating, overview, genre FROM "+ tableName);

        allMovies.write().mode(SaveMode.Append).option("header", "true").csv("hdfs://localhost/user/cloudera/table-"+ tableName);

    }
}

