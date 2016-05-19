package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Removes a relation between movie and a collection from the database
 */
public class RemoveMovieFromCollection extends Command {

    public RemoveMovieFromCollection(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int mid = Integer.parseInt(request.getParameter("mid"));

        try (Connection connection = dataSource.getConnection()) {
            CollectionDAO.removeMovieFromCollection(connection, cid, mid);
        }
    }

    //TODO remove execute method and create views
    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);
    }
}
