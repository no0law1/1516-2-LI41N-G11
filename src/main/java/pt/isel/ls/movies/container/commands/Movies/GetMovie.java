package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Get a single movie instance from the database
 */
public class GetMovie implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            Movie movie = MovieDAO.getMovie(connection, Integer.parseInt(request.getQueryParams().get("mid")));
            /**  TODO: print view  **/
        }
    }
}
