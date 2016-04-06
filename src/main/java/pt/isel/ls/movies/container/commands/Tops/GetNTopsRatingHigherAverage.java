package pt.isel.ls.movies.container.commands.Tops;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.MovieView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * TODO: comment
 */
public class GetNTopsRatingHigherAverage implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        List<Movie> movies;
        int n = Integer.parseInt(request.getQueryParams().get("n"));

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getHighestRatingMovies(connection, n);
        }

        for (Movie movie : movies) {
            new MovieView(movie).show();
        }
    }
}
