package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Creates a Collection in the database
 */
public class CreateCollection implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        int id;

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Collection collection = new Collection(name, description);

        try (Connection connection = dataSource.getConnection()) {
            id = CollectionDAO.createCollection(connection, collection);
        }

        System.out.printf("Collection id: %d\n", id);
    }
}
