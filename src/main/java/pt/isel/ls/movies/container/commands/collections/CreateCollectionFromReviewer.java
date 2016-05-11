package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Collection;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Creates a Collection in the database
 */
public class CreateCollectionFromReviewer extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Response response = Response.create(request.getHeader("file-name"));
        int id;

        String reviewerName = request.getParameter("reviewerName");
        if(reviewerName == null){
            throw new Exception("reviewerName parameter is mandatory");
        }

        try (Connection connection = dataSource.getConnection()) {
            id = CollectionDAO.createReviewerCollection(connection, reviewerName);
        }

        System.out.printf("Collection id: %d\n", id);
    }
}
