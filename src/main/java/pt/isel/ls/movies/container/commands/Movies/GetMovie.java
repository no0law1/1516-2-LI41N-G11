package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.MovieView;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Get a single movie instance from the database
 */
public class GetMovie implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        Movie movie;
        int mid = Integer.parseInt(request.getParameter("mid"));

        try(Connection connection = dataSource.getConnection()){
            movie = MovieDAO.getMovie(connection, mid);
        }

        new MovieView(movie).show();
    }
}
