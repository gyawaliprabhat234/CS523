package cs.miu.edu;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
/**
 * @author Prabhat Gyawali
 * @project BigDataProject
 */

public class HBaseScanner {
    private Configuration config;
    private final String MoviesTableName;
    private final String MoviesInfo = "movie_info";
    private final String MovieStats = "movie_stats";

    public HBaseScanner(String tableName) {
        this.config = HBaseConfiguration.create();
        MoviesTableName = tableName;
    }

    public String getValueString(Result result, String header, String columnHeader) {
        return Bytes.toString(result.getValue(Bytes.toBytes(header), Bytes.toBytes(columnHeader)));
    }

    public List<Movie> getMoviesFromScanner(ResultScanner  scanner) throws IOException {
        List<Movie> movieList = new ArrayList<>();
        Result result = scanner.next();
        while(result != null)  {
            Movie movie = new Movie();
            movie.setSeriesTitle(Bytes.toString(result.getRow()));
            movie.setReleasedYear(getValueString(result, MoviesInfo, "releasedYear"));
            movie.setRuntime(getValueString(result, MovieStats, "runtime"));
            String imdbResult = getValueString(result, MovieStats, "imdbRating");
            movie.setImdbRating(Double.parseDouble(imdbResult));
            movie.setOverview(getValueString(result, MoviesInfo, "overview"));
            movie.setGenre(getValueString(result, MoviesInfo, "genre"));
            movieList.add(movie);
            result = scanner.next();
        }
        return movieList;
    }

    public List<Movie> fetchMoviesFromHBase() throws IOException {
        List<Movie> movieList;
        try (Connection connection = ConnectionFactory.createConnection(this.config)) {
            Table table = connection.getTable(TableName.valueOf(MoviesTableName));
            Scan scan = new Scan();
            scan.setCacheBlocks(false);
            scan.setCaching(10000);
            scan.setMaxVersions(10);
            ResultScanner scanner = table.getScanner(scan);
            movieList = getMoviesFromScanner(scanner);
            System.out.println(movieList);
        }
        return movieList;
    }
}
