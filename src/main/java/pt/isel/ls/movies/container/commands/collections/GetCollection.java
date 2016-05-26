package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.collection.SingleCollectionView;
import pt.isel.ls.movies.view.collection.SingleCollectionViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Get a single collection instance from the database
 */
public class GetCollection extends Command {
    public GetCollection(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        Collection collection;
        int cid = request.getIntParameter("cid");

        int top = Integer.valueOf(request.getParameterOrDefault("top", "-1"));
        int skip = Integer.valueOf(request.getParameterOrDefault("skip", "0"));

        int count;
        List<Movie> movies;
        try (Connection connection = dataSource.getConnection()) {
            collection = CollectionDAO.getCollection(connection, cid);
            movies = MovieDAO.getCollectionMovies(connection, collection.getId(), top, skip);
            count = CollectionDAO.getCount(connection, cid);
        }

        views.put("text/html", new SingleCollectionViewHtml(collection, movies, count, top, skip));
        views.put("text/plain", new SingleCollectionView(collection));

    }
}
