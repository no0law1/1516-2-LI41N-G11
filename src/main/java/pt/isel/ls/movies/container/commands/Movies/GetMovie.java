package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Get a single movie instance from the database
 */
public class GetMovie extends Command {

    public GetMovie(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {
        throw new UnsupportedOperationException("Not implementd yet");
    }
}
