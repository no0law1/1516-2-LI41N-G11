package pt.isel.ls.movies.view;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.common.IView;

/**
 * Class whose instance represents a Movie that knows how to draw itself.
 */
public class MovieView implements IView {

    private int id;
    private String title;
    private int releaseYear;
    private String genre;

    public MovieView(int id, String title, int releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public MovieView(Movie movie) {
        this(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getGenre());
    }

    @Override
    public void show() {
        System.out.printf("%s: %s\n", "id", id);
        System.out.printf("%s: %s\n", "title", title);
        System.out.printf("%s: %s\n", "release year", releaseYear);
        System.out.printf("%s: %s\n", "genre", genre);
        System.out.println();
    }

}
