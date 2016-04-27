package pt.isel.ls.movies.container.commands.rating;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.RatingDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Rating;
import pt.isel.ls.movies.view.RatingView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets the average rating and the count to every rating of a Movie
 */
public class GetMovieRating implements ICommand{

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        List<Rating> ratings;
        int mid = Integer.parseInt(request.getParameter("mid"));

        try (Connection connection = dataSource.getConnection()) {
            ratings = RatingDAO.getMovieRatings(connection, mid);
        }

        float average = 0;
        int count = 0;
        for (Rating rating : ratings) {
            average += rating.getVal() * rating.getCount();
            count += rating.getCount();
            new RatingView(rating).show();
        }
        //TODO: Improve
        System.out.printf("Average: %s\n", average / count);
    }
}