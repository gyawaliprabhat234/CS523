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

        showTweetAnalysis(sc, spark, tableName);

        spark.stop();
        sc.close();
    }

    private static void showTweetAnalysis(JavaSparkContext sc, SparkSession spark, String tableName) throws IOException {

        JavaRDD<Movie> movieJavaRDD = sc.parallelize(new HBaseScanner(tableName).fetchMoviesFromHBase());
        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("seriesTitle", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("releasedYear", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("runtime", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("imdbRating", DataTypes.DoubleType, false));
        fields.add(DataTypes.createStructField("overview", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("genre", DataTypes.StringType, true));

        StructType schema = DataTypes.createStructType(fields);

        JavaRDD<Row> rowRDD = movieJavaRDD.map((Function<Movie, Row>) movie ->
                RowFactory.create(movie.getSeriesTitle(),
                        movie.getReleasedYear(),
                        movie.getRuntime(),
                        movie.getImdbRating(),
                        movie.getOverview(),
                        movie.getGenre()));

        Dataset<Row> dataFrame = spark.createDataFrame(rowRDD, schema);
        dataFrame.createOrReplaceTempView("movies");

        Dataset<Row> allMovies = spark.sql("SELECT seriesTitle, releasedYear, runtime,imdbRating, overview, genre FROM movies");

        allMovies.write().mode(SaveMode.Append).option("header", "true").csv("hdfs://localhost/user/cloudera/table-"+ tableName);

    }
}

