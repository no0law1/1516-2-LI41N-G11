package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.exceptions.BadRequestException;
import pt.isel.ls.movies.model.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * rating's Data Access Object
 */
public class RatingDAO {

    private static Rating map(ResultSet resultSet) throws SQLException {
        int mid = resultSet.getInt(1);
        int val = resultSet.getInt(2);
        int count = resultSet.getInt(3);
        return new Rating(mid, val, count);
    }

    /**
     * Submit a Movie rating to the database.
     *
     * @param rating rating
     * @return Unique identifier of the rating
     */
    public static Rating submitRating(Connection connection, Rating rating) throws Exception {
        if (rating.getVal() == -1) {
            throw new BadRequestException("You missed some parameters");
        }

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from rating where mid=? and val=?");
        preparedStatement.setInt(1, rating.getMid());
        preparedStatement.setInt(2, rating.getVal());

        try(ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update rating set count=count+1 where mid=? and val=?");
            } else {
                preparedStatement = connection.prepareStatement("insert into rating(mid, val, count) values (?, ?, 1)");
            }
            preparedStatement.setInt(1, rating.getMid());
            preparedStatement.setInt(2, rating.getVal());
            if(preparedStatement.executeUpdate() != 0){
                return rating;
            }
        }
        throw new InsertException("Couldn't submit rating");
    }

    /**
     * Gets all of a specific movie ratings.
     * 
     * @param mid Unique identifier for the movie ratings.
     * @return List of the movie ratings
     */
    public static List<Rating> getMovieRatings(Connection connection, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from rating where mid=?");
        preparedStatement.setInt(1, mid);

        List<Rating> ratings = new LinkedList<>();
        try(ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                Rating rating = map(resultSet);
                ratings.add(rating);
            }
        }
        return ratings;
    }
}
