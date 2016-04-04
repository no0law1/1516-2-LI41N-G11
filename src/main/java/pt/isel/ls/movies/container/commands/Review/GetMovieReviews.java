package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Gets all reviews of a Movie from the database
 */
public class GetMovieReviews extends Command {

    public GetMovieReviews(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {

    }
}
