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
public class PostMovieToCollection extends Command.RedirectViewCommand {

    private static final String DETAILS = "Adds a movie to a collection";

    private static final String METHOD = "POST";

    private static final String PATH = "/collections/{cid}/movies";

    public PostMovieToCollection(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public String doWork(Request request) throws Exception {
        Collection.MovieCollectionUID movieCollectionUID;
        int cid = request.getIntParameter("cid");
        int mid = request.getIntParameter("mid");

        try (Connection connection = dataSource.getConnection()) {
            movieCollectionUID = CollectionDAO.postMovieToCollection(connection, cid, mid);
        }

        System.out.printf("cid: %d, mid: %d\n", movieCollectionUID.cid, movieCollectionUID.mid);

        return new StringBuilder("/collections/").append(cid).append("/movies").toString();
    }

    //TODO remove execute method and create views
    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);
    }
}
