package cs.miu.edu;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;

public class HBaseTable {

    private final String MovieTableName;
    private final Configuration config;

    public HBaseTable(String tableName) {
        this.MovieTableName = tableName;
        this.config = HBaseConfiguration.create();
    }
    private final String MoviesInfo = "movie_info";
    private final String MovieStats = "movie_stats";
    private final String Stars = "stars";

    public void insertRow(Movie movie) throws IOException {
        System.out.println("Incoming Message - Consuming -> {\n " + movie + "\n }");
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            HTableDescriptor movieTableDesc = new HTableDescriptor(
                    TableName.valueOf(MovieTableName));
            movieTableDesc.addFamily(new HColumnDescriptor(MoviesInfo));
            movieTableDesc.addFamily(new HColumnDescriptor(MovieStats));
            movieTableDesc.addFamily(new HColumnDescriptor(Stars));

            if (!admin.tableExists(movieTableDesc.getTableName())) {
                admin.createTable(movieTableDesc);
                System.out.println("Created Table");
            }

            // Get Connection to Table
            Table movieTable = connection.getTable(TableName
                    .valueOf(MovieTableName));

            Put row = new Put(Bytes.toBytes(movie.getSeriesTitle()));

            row.addColumn(Bytes.toBytes(MoviesInfo),
                    Bytes.toBytes("releasedYear"), Bytes.toBytes(movie.getReleasedYear()));

            row.addColumn(Bytes.toBytes(MoviesInfo),
                    Bytes.toBytes("director"), Bytes.toBytes(movie.getDirector()));

            row.addColumn(Bytes.toBytes(MoviesInfo),
                    Bytes.toBytes("genre"), Bytes.toBytes(movie.getGenre()));
            row.addColumn(Bytes.toBytes(MoviesInfo),
                    Bytes.toBytes("posterLink"), Bytes.toBytes(movie.getPosterLink()));

            row.addColumn(Bytes.toBytes(MoviesInfo),
                    Bytes.toBytes("overview"), Bytes.toBytes(movie.getOverview()));

            row.addColumn(Bytes.toBytes(MovieStats),
                    Bytes.toBytes("metaScore"), Bytes.toBytes(movie.getMetaScore().toString()));

            row.addColumn(Bytes.toBytes(MovieStats),
                    Bytes.toBytes("runtime"), Bytes.toBytes(movie.getRuntime()));
            row.addColumn(Bytes.toBytes(MovieStats),
                    Bytes.toBytes("imdbRating"), Bytes.toBytes(movie.getImdbRating().toString()));

            row.addColumn(Bytes.toBytes(Stars),
                    Bytes.toBytes("star1"), Bytes.toBytes(movie.getStar1()));
            row.addColumn(Bytes.toBytes(Stars),
                    Bytes.toBytes("star2"), Bytes.toBytes(movie.getStar2()));
            row.addColumn(Bytes.toBytes(Stars),
                    Bytes.toBytes("star3"), Bytes.toBytes(movie.getStar3()));
            row.addColumn(Bytes.toBytes(Stars),
                    Bytes.toBytes("star4"), Bytes.toBytes(movie.getStar4()));

            movieTable.put(row);

        }

    }

}
