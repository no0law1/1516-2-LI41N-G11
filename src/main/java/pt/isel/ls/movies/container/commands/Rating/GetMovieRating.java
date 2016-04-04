package pt.isel.ls.movies.container.commands.Rating;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Gets the average Rating and the count to every rating of a Movie
 */
public class GetMovieRating implements ICommand{

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {

    }
}
