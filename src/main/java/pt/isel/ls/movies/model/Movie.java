package pt.isel.ls.movies.model;

import java.util.List;

/**
 * Class whose instance represents a movie
 */
public class Movie {
    private int id;
    private String title;
    private int releaseYear;
    private String genre;

    private List<Review> reviews;

    public Movie(int id, String title, int releaseYear, String genre){
        this(id, title, releaseYear, genre, null);
    }

    public Movie(String title, int releaseYear, String genre){
        this(0, title, releaseYear, genre, null);
    }

    public Movie(int id, String title, int releaseYear, String genre, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Movie){
            Movie movie = (Movie)o;
            return this.id == movie.id &&
                    this.genre.equals(movie.genre) &&
                    this.title.equals(movie.title) &&
                    this.releaseYear == movie.releaseYear;
        }
        return false;
    }

    public enum Sort{
        ADDED_DATE,
        ADDED_DATE_DESC,
        YEAR,
        YEAR_DESC,
        TITLE,
        TITLE_DESC,
        RATING,
        RATING_DESC
    }
}
