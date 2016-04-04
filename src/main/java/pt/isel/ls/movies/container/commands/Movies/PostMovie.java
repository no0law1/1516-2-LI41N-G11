package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Submits a Movie to the database
 */
public class PostMovie extends Command {

    public PostMovie(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
