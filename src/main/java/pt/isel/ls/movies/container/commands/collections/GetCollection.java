package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.CollectionView;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Get a single collection instance from the database
 */
public class GetCollection implements ICommand {
    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Collection collection;
        int cid = Integer.parseInt(request.getParameter("cid"));

        try (Connection connection = dataSource.getConnection()) {
            collection = CollectionDAO.getCollection(connection, cid);
        }

        new CollectionView(collection).show();
    }
}
