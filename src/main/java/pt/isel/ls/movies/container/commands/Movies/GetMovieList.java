package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Gets all movie instances from the database
 */
public class GetMovieList implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {

    }
}
