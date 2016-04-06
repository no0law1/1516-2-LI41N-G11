package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.MovieView;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * TODO: Commentary.
 */
public class GetMostReviewedMovie implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Movie movie;

        try (Connection connection = dataSource.getConnection()) {
            movie = MovieDAO.getMostReviewedMovie(connection);
        }

        new MovieView(movie).show();
    }

}
