package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.MovieView;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Command that gets the movie with the highest average rating.
 */
public class GetHighestRatedMovie implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Movie movie;

        try (Connection connection = dataSource.getConnection()) {
            movie = MovieDAO.getHighestRatingMovie(connection);
        }

        new MovieView(movie).show();
    }
}
