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

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Response response = Response.create(request.getHeader("file-name"));
        List<Review> reviews;
        int mid = Integer.parseInt(request.getParameter("mid"));

        try (Connection connection = dataSource.getConnection()) {
            reviews = ReviewDAO.getReviews(connection, mid);
        }

        views.put("text/html", new ReviewsViewHtml(reviews));
        views.put("text/plain", new ReviewsView(reviews));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.write(getView(request.getHeaderOrDefault("accept", "text/html")));
    }
}
