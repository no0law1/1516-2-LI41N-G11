package pt.isel.ls.movies.container.commands.Rating;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Submits a rating of a Movie to the database
 */
public class PostRating implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {

    }
}
