package pt.isel.ls.movies.container.commands.Rating;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.engine.Request;

/**
 * Gets the average Rating and the count to every rating of a Movie
 */
public class GetMovieRating extends Command {

    public GetMovieRating(String commandRoute) {
        super(commandRoute);
    }

    @Override
    public void execute(Request request) throws Exception {

    }
}
