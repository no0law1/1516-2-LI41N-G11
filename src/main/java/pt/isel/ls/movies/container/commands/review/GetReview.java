package pt.isel.ls.movies.container.commands.review;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Review;
import pt.isel.ls.movies.view.ReviewView;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Gets a single review of a Movie from the database
 */
public class GetReview implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Review review;
        int mid = Integer.parseInt(request.getParameter("mid"));
        int id = Integer.parseInt(request.getParameter("rid"));

        try (Connection connection = dataSource.getConnection()) {
            review = ReviewDAO.getReview(connection, new Review.ReviewUID(mid, id));
        }

        new ReviewView(review).show();
    }
}
