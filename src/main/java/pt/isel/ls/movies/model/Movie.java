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
    private float averageRating;


    public Movie(String title, int releaseYear, String genre){
        this(0, title, releaseYear, genre, 0);
    }

    public Movie(int id, String title, int releaseYear, String genre) {
        this(id, title, releaseYear, genre, 0);
    }

    public Movie(int id, String title, int releaseYear, String genre, float averageRating) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.averageRating = averageRating;
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

    public float getAverageRating() {
        return averageRating;
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
