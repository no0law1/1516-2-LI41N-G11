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
public class GetCollections extends Command {
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

        views.put("text/html", new CollectionsViewHtml(collections));
        views.put("text/plain", new CollectionsView(collections));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        System.out.println(getView(request.getHeaderOrDefault("accept", "text/html")));
    }
}
