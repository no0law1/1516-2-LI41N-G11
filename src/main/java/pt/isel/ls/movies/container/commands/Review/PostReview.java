package pt.isel.ls.movies.container.commands.Review;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.engine.Request;

import javax.sql.DataSource;

/**
 * Submits a review of a Movie in the database
 */
public class PostReview implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
//        Review.ReviewUID reviewUID;
//        Review review = new Review()
//
//        try (Connection connection = dataSource.getConnection()) {
//            reviewUID = ReviewDAO.submitReview(connection, review);
//        }
//
//        new ReviewView(review).show();
    }
}
