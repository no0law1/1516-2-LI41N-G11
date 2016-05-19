package pt.isel.ls.movies.container.commands.review;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.review.SingleReviewView;
import pt.isel.ls.movies.view.review.SingleReviewViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Gets a single review of a Movie from the database
 */
public class GetReview extends Command {

    public GetReview(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {

        Review review;
        int mid = Integer.parseInt(request.getParameter("mid"));
        int id = Integer.parseInt(request.getParameter("rid"));

        try (Connection connection = dataSource.getConnection()) {
            review = ReviewDAO.getReview(connection, new Review.ReviewUID(mid, id));
        }

        views.put("text/html", new SingleReviewViewHtml(review));
        views.put("text/plain", new SingleReviewView(review));
    }
}
