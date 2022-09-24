package com.miu.edu;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * @author Prabhat Gyawali
 * @created 22-Sep-2022 - 11:17 AM
 * @project BigDataProject
 */

@Data
public class Movie {

    @CsvBindByName(column = "Poster_Link")
    private String posterLink;

    @CsvBindByName(column = "Series_Title")
    private String seriesTitle;

    @CsvBindByName(column = "Released_Year")
    private String releasedYear;

    @CsvBindByName(column = "Certificate")
    private String certificate;

    @CsvBindByName(column = "Runtime")
    private String runtime;

    @CsvBindByName(column = "Genre")
    private String genre;

    @CsvBindByName(column = "IMDB_Rating")
    private Double imdbRating;

    @CsvBindByName(column = "Overview")
    private String overview;

    @CsvBindByName(column = "Meta_score")
    private Integer metaScore;

    @CsvBindByName(column = "Director")
    private String director;

    @CsvBindByName(column = "Star1")
    private String star1;

    @CsvBindByName(column = "Star2")
    private String star2;

    @CsvBindByName(column = "Star3")
    private String star3;

    @CsvBindByName(column = "Star4")
    private String star4;

    @CsvBindByName(column = "No_of_Votes")
    private Integer noOfVotes;

    @CsvBindByName(column = "Gross")
    private String gross;
}