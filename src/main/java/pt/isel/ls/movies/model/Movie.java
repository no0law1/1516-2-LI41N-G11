package pt.isel.ls.movies.model;

/**
 * Class whose instance represents a movie
 */
public class Movie {
    private int id;
    private String title;
    private int releaseYear;
    private String genre;

    public Movie(int id, String title, int releaseYear, String genre){
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public Movie(String title, int releaseYear, String genre){
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
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
}
