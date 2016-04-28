package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Removes a relation between movie and a collection from the database
 */
public class RemoveMovieFromCollection extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int mid = Integer.parseInt(request.getParameter("mid"));

        try (Connection connection = dataSource.getConnection()) {
            CollectionDAO.removeMovieFromCollection(connection, cid, mid);
        }
    }
}
