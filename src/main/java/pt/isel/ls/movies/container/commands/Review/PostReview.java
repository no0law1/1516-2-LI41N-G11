package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Submits a review of a Movie in the database
 */
public class PostReview extends Command {

    public PostReview(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {

    }
}
