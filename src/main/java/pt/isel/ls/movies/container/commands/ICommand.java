package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

/**
 * Contract of a Command class.
 */
public interface ICommand {

    String getMethod();

    String getPath();

    void execute(Request request, Response response) throws Exception;
}
