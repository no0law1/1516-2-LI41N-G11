package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * TODO: comment
 */
public interface ICommand {

    void execute(DataSource dataSource, Request request) throws Exception;
}
