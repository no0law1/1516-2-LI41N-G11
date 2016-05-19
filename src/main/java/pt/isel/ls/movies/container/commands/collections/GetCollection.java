package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.collection.SingleCollectionView;
import pt.isel.ls.movies.view.collection.SingleCollectionViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;

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
        int cid = Integer.parseInt(request.getParameter("cid"));

        try (Connection connection = dataSource.getConnection()) {
            collection = CollectionDAO.getCollection(connection, cid);
        }

        views.put("text/html", new SingleCollectionViewHtml(collection));
        views.put("text/plain", new SingleCollectionView(collection));

    }
}
