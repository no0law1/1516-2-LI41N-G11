package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.collection.CreatedCollectionView;
import pt.isel.ls.movies.view.collection.CreatedCollectionViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Creates a Collection in the database
 */
public class CreateCollection extends Command.RedirectViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new CreateCollection(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("POST", "/collections", "name=?&description=?", "Creates a collection");
        }
    };

    public CreateCollection(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String doWork(Request request) throws Exception {
        int id;

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Collection collection = new Collection(name, description);

        try (Connection connection = dataSource.getConnection()) {
            id = CollectionDAO.createCollection(connection, collection);
        }

        collection.setId(id);

        views.put("text/html", new CreatedCollectionViewHtml(collection));
        views.put("text/plain", new CreatedCollectionView(collection));

        return new StringBuilder("/collections/").append(id).append("/movies").toString();
    }
}
