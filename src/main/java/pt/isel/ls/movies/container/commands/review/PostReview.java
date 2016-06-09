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
public class PostReview extends Command.RedirectViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new PostReview(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("POST", "/movies/{mid}/reviews", "reviewerName=?&reviewSummary=?&review=?&rating=?", "Creates a review for a movie");
        }
    };

    public PostReview(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String doWork(Request request) throws Exception {
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

        return new StringBuilder("/movies/").append(mid).append("/reviews").toString();
    }
}
