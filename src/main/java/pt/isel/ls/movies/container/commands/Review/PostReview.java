package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Review;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a review of a Movie in the database
 */
public class PostReview implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Review.ReviewUID reviewUID;
        int mid = Integer.parseInt(request.get("mid"));
        String reviewerName = request.get("reviewerName");
        String reviewSummary = request.get("reviewSummary");
        String reviewExtended = request.get("review");
        int rating = Integer.parseInt(request.get("rating"));
        Review review = new Review(mid, reviewerName, reviewSummary, reviewExtended, rating);

        try (Connection connection = dataSource.getConnection()) {
            reviewUID = ReviewDAO.submitReview(connection, review);
        }

        System.out.printf("mid: %d, id: %d\n", reviewUID.mid, reviewUID.id);
    }
}
