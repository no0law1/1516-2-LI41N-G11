package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Collection;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Posts a relation of movie and collection to the database
 */
public class PostMovieToCollection extends Command {
    public PostMovieToCollection(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
        Collection.MovieCollectionUID movieCollectionUID;
        int cid = Integer.parseInt(request.getParameter("cid"));
        int mid = Integer.parseInt(request.getParameter("mid"));

        try (Connection connection = dataSource.getConnection()) {
            movieCollectionUID = CollectionDAO.postMovieToCollection(connection, cid, mid);
        }

        System.out.printf("cid: %d, mid: %d\n", movieCollectionUID.cid, movieCollectionUID.mid);
    }
}
