package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Gets all movie instances from the database
 */
public class GetMovieList extends Command {

    public GetMovieList(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {

    }
}
