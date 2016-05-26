package pt.isel.ls.movies.container.commands.review;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.review.ReviewsView;
import pt.isel.ls.movies.view.review.ReviewsViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets all reviews of a Movie from the database
 */
public class GetMovieReviews extends Command {

    public GetMovieReviews(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void doWork(Request request) throws Exception {
        List<Review> reviews;
        int mid = request.getIntParameter("mid");

        int top = Integer.valueOf(request.getParameterOrDefault("top", "-1"));
        int skip = Integer.valueOf(request.getParameterOrDefault("skip", "0"));

        int count;
        try (Connection connection = dataSource.getConnection()) {
            reviews = ReviewDAO.getReviews(connection, mid,
                    top,
                    skip);
            count = ReviewDAO.getCount(connection, mid);
        }

        views.put("text/html", new ReviewsViewHtml(mid, reviews, count, top, skip));
        views.put("text/plain", new ReviewsView(reviews));
    }
}
