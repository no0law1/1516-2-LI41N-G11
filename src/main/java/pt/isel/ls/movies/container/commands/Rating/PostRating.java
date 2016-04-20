package pt.isel.ls.movies.container.commands.Rating;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.RatingDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Rating;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a rating of a Movie to the database
 */
public class PostRating implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        int mid = Integer.parseInt(request.getParameter("mid"));
        int val = Integer.parseInt(request.getParameter("rating"));

        Rating rating = new Rating(mid, val);

        try (Connection connection = dataSource.getConnection()) {
            rating = RatingDAO.submitRating(connection, rating);
        }

        System.out.printf("Rating mid: %d, val: %d\n", rating.getMid(), rating.getVal());
    }
}
