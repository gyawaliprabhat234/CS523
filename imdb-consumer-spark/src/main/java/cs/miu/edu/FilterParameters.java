package cs.miu.edu;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Prabhat Gyawali
 * @created 23-Sep-2022 - 12:13 PM
 * @project BigDataProject
 */
public class FilterParameters {
    private String releaseYear;
    private Set<String> genres;
    private String tableName = "movies";

    FilterParameters(String... args) {
    	if(args == null) return;
        releaseYear = args.length  > 0 && !args[0].isEmpty() ?  args[0]: "skip";
        releaseYear = releaseYear.equals("skip") ? null: releaseYear;
        genres = args.length > 1 && !args[1].isEmpty() ? new HashSet<>(Arrays.asList(args[1].split(","))): null;
        if (args.length > 2) {
            System.out.println("Invalid passed command line parameters: \n Only Accept 2 arguments first is release year and second is genres with comma seperated");
            System.exit(0);
        }
    }

    public String getTableName() {
        List<String> tableStringList = new ArrayList<>();
        tableStringList.add(this.tableName);
        if (releaseYear != null && !releaseYear.isEmpty()) {
            tableStringList.add(this.releaseYear);
        }
        if (this.genres != null && !this.genres.isEmpty()) {
            tableStringList.addAll(this.genres);
        }
        return tableStringList.stream().collect(Collectors.joining("_"));
    }

    public boolean checkReleaseYear(Movie movie) {
        String releaseYear = movie.getReleasedYear();
        if (this.releaseYear == null || this.releaseYear.isEmpty()) {
            return true;
        }
        return this.releaseYear.equals(releaseYear);
    }

    public boolean checkGenres(Movie movie) {
        String genres = movie.getGenre();
        if (this.genres == null || this.genres.isEmpty()) return true;
        return this.genres.stream().anyMatch(genres::contains);
    }

    @Override
    public String toString() {
        return "FilterParameters{" +
                "releaseYear='" + releaseYear + '\'' +
                ", genres=" + genres +
                ", tableName='" + tableName + '\'' +
                '}';
    }


}
