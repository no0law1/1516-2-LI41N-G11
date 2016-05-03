package pt.isel.ls.movies.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Class whose instance represents a collection
 */
public class Collection {

    public static class MovieCollectionUID {

        public int cid;
        public int mid;

        public MovieCollectionUID(int cid, int mid) {
            this.cid = cid;
            this.mid = mid;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MovieCollectionUID)) {
                return false;
            }
            MovieCollectionUID movieCollectionUID = (MovieCollectionUID) o;
            return this.mid == movieCollectionUID.mid && this.cid == movieCollectionUID.cid;
        }
    }


    private int id;
    private String name;
    private String description;

    private List<Movie> movies;


    public Collection(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        movies = new LinkedList<>();
    }

    public Collection(String name, String description) {
        this(-1, name, description);
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

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Collection) {
            Collection collection = (Collection) o;
            return this.id == collection.id &&
                    this.name.equals(collection.name) &&
                    this.description.equals(collection.description);
        }
        return false;
    }
}
