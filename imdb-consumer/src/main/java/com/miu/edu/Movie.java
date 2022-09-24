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

    private String posterLink;

    private String seriesTitle;

    private String releasedYear;

    private String certificate;

    private String runtime;

    private String genre;

    private Double imdbRating;

    private String overview;

    private Integer metaScore;

    private String director;

    private String star1;

    private String star2;

    private String star3;

    private String star4;

    private Integer noOfVotes;

    private String gross;
}