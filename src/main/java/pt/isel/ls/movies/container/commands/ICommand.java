package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.engine.Request;

/**
 * Contract of a Command class.
 */
public interface ICommand {

    void execute(Request request) throws Exception;
}
