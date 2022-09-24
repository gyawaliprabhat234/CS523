package cs.miu.edu;

import lombok.Data;

/**
 * @author Prabhat Gyawali
 * @created 22-Sep-2022 - 11:17 AM
 * @project BigDataProject
 */

@Data
public class Movie {

    public String getPosterLink() {
		return posterLink;
	}

	public String getSeriesTitle() {
		return seriesTitle;
	}

	public String getReleasedYear() {
		return releasedYear;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getRuntime() {
		return runtime;
	}

	public String getGenre() {
		return genre;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public String getOverview() {
		return overview;
	}

	public Integer getMetaScore() {
		return metaScore;
	}

	public String getDirector() {
		return director;
	}

	public String getStar1() {
		return star1;
	}

	public String getStar2() {
		return star2;
	}

	public String getStar3() {
		return star3;
	}

	public String getStar4() {
		return star4;
	}

	public Integer getNoOfVotes() {
		return noOfVotes;
	}

	public String getGross() {
		return gross;
	}

	private String posterLink;

    private String seriesTitle;

    private String releasedYear;

    @Override
	public String toString() {
		return "Movie [posterLink=" + posterLink + ", seriesTitle="
				+ seriesTitle + ", releasedYear=" + releasedYear
				+ ", certificate=" + certificate + ", runtime=" + runtime
				+ ", genre=" + genre + ", imdbRating=" + imdbRating
				+ ", overview=" + overview + ", metaScore=" + metaScore
				+ ", director=" + director + ", star1=" + star1 + ", star2="
				+ star2 + ", star3=" + star3 + ", star4=" + star4
				+ ", noOfVotes=" + noOfVotes + ", gross=" + gross + "]";
	}

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