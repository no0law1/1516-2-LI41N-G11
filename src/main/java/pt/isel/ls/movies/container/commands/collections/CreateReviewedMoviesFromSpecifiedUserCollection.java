package pt.isel.ls.movies.container.commands.collections;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.CollectionDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Collection;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Creates a new {@link Collection} with all the movies reviewed by the specified reviewer
 */
public class CreateReviewedMoviesFromSpecifiedUserCollection extends Command {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        String reviewer = request.getParameterOrDefault("reviewerName", null);
        if(reviewer == null){
            throw new IllegalArgumentException("There must exist a 'reviewerName' parameter");
        }
        int id;
        try(Connection connection = dataSource.getConnection()){
            id = CollectionDAO.createCollectionWithMoviesReviewedBy(connection, reviewer);
        }

        System.out.println("Collection inserted with the id: "+ id);
    }
}
