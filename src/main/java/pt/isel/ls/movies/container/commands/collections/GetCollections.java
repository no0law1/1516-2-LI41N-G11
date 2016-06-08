package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.collection.CollectionsView;
import pt.isel.ls.movies.view.collection.CollectionsViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Get all collection instances from the database
 */
public class GetCollections extends Command.ViewCommand {

    private static final String DETAILS = "Gets all collections";

    private static final String METHOD = "GET";

    private static final String PATH = "/collections";

    public GetCollections(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void doWork(Request request) throws Exception {
        List<Collection> collections;

        int top = Integer.valueOf(request.getParameterOrDefault("top", "-1"));
        int skip = Integer.valueOf(request.getParameterOrDefault("skip", "0"));

        int count;
        try (Connection connection = dataSource.getConnection()) {
            collections = CollectionDAO.getCollections(
                    connection,
                    top,
                    skip
            );
            count = CollectionDAO.getCount(connection);
        }

        views.put("text/html", new CollectionsViewHtml(collections, count, top, skip));
        views.put("text/plain", new CollectionsView(collections));
    }
}
