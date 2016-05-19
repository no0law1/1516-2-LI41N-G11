package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;

/**
 * Class whose instance represents a Movie that knows how to draw itself.
 */
public class SingleMovieView implements Writable {

    private StringBuffer view;

    public SingleMovieView(Movie movie) {
        this(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getGenre());
    }

    public SingleMovieView(int id, String title, int releaseYear, String genre) {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "id", id));
        view.append(String.format("%s: %s\n", "title", title));
        view.append(String.format("%s: %s\n", "release year", releaseYear));
        view.append(String.format("%s: %s\n", "genre", genre));
        view.append("\n");
    }

    public String getView() {
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
