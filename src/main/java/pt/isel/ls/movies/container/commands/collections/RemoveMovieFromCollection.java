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
public class RemoveMovieFromCollection extends Command.RedirectViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new RemoveMovieFromCollection(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("DELETE", "/collections/{cid}/movies/{mid}", null, "Removes a movie from a collection");
        }
    };

    public RemoveMovieFromCollection(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String doWork(Request request) throws Exception {
        int cid = request.getIntParameter("cid");
        int mid = request.getIntParameter("mid");

        try (Connection connection = dataSource.getConnection()) {
            CollectionDAO.removeMovieFromCollection(connection, cid, mid);
        }
        return new StringBuilder("/collections/").append(cid).append("/movies").toString();
    }

    //TODO remove execute method and create views
    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);
    }
}
