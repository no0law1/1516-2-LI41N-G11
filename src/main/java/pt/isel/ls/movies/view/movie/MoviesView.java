package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Plain text view of a set of {@link Movie}
 */
public class MoviesView implements Writable {

    private StringBuilder builder;

    public MoviesView(List<Movie> movies) {
        builder = new StringBuilder();
        if (movies.isEmpty()) {
            builder.append("There are no Movies\n");
            return;
        }
        movies.forEach(movie -> builder.append(new SingleMovieView(movie).getView()));
    }

    public String getView() {
        return builder.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
