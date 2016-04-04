package pt.isel.ls.movies.container.commands.Rating;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Submits a rating of a Movie to the database
 */
public class PostRating extends Command {

    public PostRating(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {

    }
}
