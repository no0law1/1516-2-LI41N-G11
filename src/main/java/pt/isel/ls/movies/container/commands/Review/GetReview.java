package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Gets a single review of a Movie from the database
 */
public class GetReview extends Command {

    public GetReview(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
