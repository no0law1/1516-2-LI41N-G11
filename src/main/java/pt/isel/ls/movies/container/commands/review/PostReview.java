package pt.isel.ls.movies.container.commands.review;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.ReviewDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Review;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a review of a Movie in the database
 */
public class PostReview extends Command {

    private static final String DETAILS = "Creates a review for a movie";

    private static final String METHOD = "POST";

    private static final String PATH = "/movies/{mid}/reviews";

    public PostReview(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void doWork(Request request) throws Exception {
        Review.ReviewUID reviewUID;
        int mid = request.getIntParameter("mid");

        String reviewerName = request.getParameter("reviewerName");
        String reviewSummary = request.getParameter("reviewSummary");
        String reviewExtended = request.getParameter("review");
        int rating = Integer.parseInt(request.getParameter("rating"));
        Review review = new Review(mid, reviewerName, reviewSummary, reviewExtended, rating);

        try (Connection connection = dataSource.getConnection()) {
            reviewUID = ReviewDAO.submitReview(connection, review);
        }

        System.out.printf("mid: %d, id: %d\n", reviewUID.mid, reviewUID.id);
    }

    //TODO remove execute method and create views
    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);
    }
}
