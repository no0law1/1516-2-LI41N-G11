package pt.isel.ls.movies.model;

import java.util.List;

/**
 * Class whose instance represents a collection
 */
public class Collection {

    private int id;
    private String name;
    private String description;

    private List<Movie> movies;


    public Collection(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
