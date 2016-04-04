package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Gets all reviews of a Movie from the database
 */
public class GetMovieReviews implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {

    }
}
