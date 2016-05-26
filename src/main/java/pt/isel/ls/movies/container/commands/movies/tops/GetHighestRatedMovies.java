package pt.isel.ls.movies.container.commands.movies.tops;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.MoviesView;
import pt.isel.ls.movies.view.movie.MoviesViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Command that gets the n movies with the highest average rating.
 */
public class GetHighestRatedMovies extends Command {

    public GetHighestRatedMovies(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        List<Movie> movies;
        int n = Integer.parseInt(request.getParameter("n"));

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getHighestRatingMovies(connection, n);
        }

        views.put("text/html", new MoviesViewHtml(" - " + n + " Highest Rated Movies", movies, n, -1, 0, null));
        views.put("text/plain", new MoviesView(movies));
    }
}
