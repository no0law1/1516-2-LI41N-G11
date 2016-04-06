package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.ReviewView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets all reviews of a Movie from the database
 */
public class GetMovieReviews implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        List<Review> reviews;
        int mid = Integer.parseInt(request.getQueryParams().get("mid"));

        try (Connection connection = dataSource.getConnection()) {
            reviews = ReviewDAO.getReviews(connection, mid);
        }

        for (Review review : reviews) {
            new ReviewView(review).show();
        }
    }
}
