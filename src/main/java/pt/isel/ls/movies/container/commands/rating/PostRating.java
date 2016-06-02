package pt.isel.ls.movies.container.commands.rating;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.RatingDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Rating;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a rating of a Movie to the database
 */
public class PostRating extends Command {

    private static final String DETAILS = "Posts a movie rating";

    private static final String METHOD = "POST";

    private static final String PATH = "/movies/{mid}/ratings";

    public PostRating(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void doWork(Request request) throws Exception {
        int mid = request.getIntParameter("mid");
        int val = request.getIntParameter("rating");

        Rating rating = new Rating(mid, val);

        try (Connection connection = dataSource.getConnection()) {
            rating = RatingDAO.submitRating(connection, rating);
        }

        System.out.printf("rating mid: %d, val: %d\n", rating.getMid(), rating.getVal());
    }

    //TODO remove execute method and create views
    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);
    }
}
