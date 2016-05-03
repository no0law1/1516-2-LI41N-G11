package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.common.IView;

import java.util.List;

/**
 * Plain text view of a set of {@link Movie}
 */
public class MoviesView implements IView {

    private List<Movie> movies;

    public MoviesView(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String getView() {
        if (movies.isEmpty()) {
            return "There are no Movies\n";
        }
        StringBuilder builder = new StringBuilder();

        movies.forEach(movie -> builder.append(new SingleMovieView(movie).getView()));

        return builder.toString();
    }
}
