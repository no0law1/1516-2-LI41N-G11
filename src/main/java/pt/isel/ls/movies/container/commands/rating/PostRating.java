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
public class PostRating extends Command.RedirectViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new PostRating(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("POST", "/movies/{mid}/ratings", null, "Posts a movie rating");
        }
    };

    public PostRating(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String doWork(Request request) throws Exception {
        int mid = request.getIntParameter("mid");
        int val = request.getIntParameter("rating");

        Rating rating = new Rating(mid, val);

        try (Connection connection = dataSource.getConnection()) {
            rating = RatingDAO.submitRating(connection, rating);
        }

        System.out.printf("rating mid: %d, val: %d\n", rating.getMid(), rating.getVal());

        return new StringBuilder("/movies/").append(mid).append("/ratings").toString();
    }
}
