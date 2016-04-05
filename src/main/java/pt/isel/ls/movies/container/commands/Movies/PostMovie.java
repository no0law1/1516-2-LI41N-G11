package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Submits a Movie to the database
 */
public class PostMovie implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        //TODO: implement
    }
}
