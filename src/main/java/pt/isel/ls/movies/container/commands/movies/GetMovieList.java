package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.MovieView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets all movie instances from the database
 */
public class GetMovieList implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        List<Movie> movies;

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getMovies(
                    connection,
                    Integer.valueOf(request.getParameterOrDefault("top", "-1")),
                    Integer.valueOf(request.getParameterOrDefault("skip", "0"))
            );
        }

        for (Movie movie : movies) {
            new MovieView(movie).show();
        }
    }
}
