package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.CollectionView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Get all collection instances from the database
 */
public class GetCollections implements ICommand {
    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        List<Collection> collections;

        try (Connection connection = dataSource.getConnection()) {
            collections = CollectionDAO.getCollections(
                    connection,
                    Integer.valueOf(request.getParameterOrDefault("top", "-1")),
                    Integer.valueOf(request.getParameterOrDefault("skip", "0"))
            );
        }

        for (Collection collection : collections) {
            System.out.println(new CollectionView(collection).getView());
        }
    }
}
