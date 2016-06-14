package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Posts a relation of movie and collection to the database
 */
public class PostMovieToCollection extends Command.RedirectViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new PostMovieToCollection(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("POST", "/collections/{cid}/movies", "mid=?", "Adds a movie to a collection");
        }
    };

    public PostMovieToCollection(DataSource dataSource) {
        super(dataSource);
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

        return new StringBuilder("/collections/").append(cid).toString();
    }
}
