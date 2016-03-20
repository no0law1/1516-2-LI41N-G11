package pt.isel.ls.movies.model;

/**
 * Class whose instance represents a movie
 */
public class Movie {
    public int id;
    public String title;
    public int releaseYear;
    public int genre;

    public Movie(int id, String title, int releaseYear, int genre){
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

}
