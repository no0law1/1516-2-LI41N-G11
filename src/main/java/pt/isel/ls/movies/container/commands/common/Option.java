package pt.isel.ls.movies.container.commands.common;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Lists all the commands available and their description
 */
public class Option implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        //TODO: get from file?
        throw new UnsupportedOperationException();
    }

}
