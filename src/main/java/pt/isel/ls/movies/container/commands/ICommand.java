package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.engine.Request;

/**
 * TODO: comment
 */
public interface ICommand {

    void execute(Request request) throws Exception;
}
