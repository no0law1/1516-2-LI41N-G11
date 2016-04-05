package pt.isel.ls.movies.data.entity;

import pt.isel.ls.movies.data.exceptions.InsertException;
import pt.isel.ls.movies.data.exceptions.NoDataException;
import pt.isel.ls.movies.model.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Rating's Data Access Object
 */
public class RatingDAO {

    /**
     * Submit a Movie Rating to the database.
     *
     * @param rating Rating
     * @return Unique identifier of the Rating
     */
    public static Rating submitRating(Connection connection, Rating rating) throws Exception {
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
        throw new InsertException("Cannot submit rating");
    }

    /**
     * Gets all of a specific movie ratings.
     * 
     * @param mid Unique identifier for the movie ratings.
     * @return List of the movie ratings
     */
    public static List<Rating> getMovieRatings(Connection connection, int mid) throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select val, count from rating where mid=?");
        preparedStatement.setInt(1, mid);

        List<Rating> ratings = new LinkedList<>();
        try(ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                int val = resultSet.getInt(1);
                int count = resultSet.getInt(2);
                Rating rating = new Rating(mid, val, count);
                ratings.add(rating);
            }
        }
        if(ratings.isEmpty()){
            throw new NoDataException();
        }
        return ratings;
    }
}
