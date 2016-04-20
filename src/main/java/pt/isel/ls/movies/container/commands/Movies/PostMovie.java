package pt.isel.ls.movies.container.commands.Movies;

import pt.isel.ls.movies.container.commands.ICommand;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a Movie to the database
 */
public class PostMovie implements ICommand {

    @Override
    public void execute(DataSource dataSource, Request request) throws Exception {
        int mid;

        String title = request.getParameter("title");
        int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
        String genre = request.getParameter("genre");
        Movie movie = new Movie(title, releaseYear, genre);

        try (Connection connection = dataSource.getConnection()) {
            mid = MovieDAO.submitMovie(connection, movie);
        }

        System.out.printf("mid: %d\n", mid);
    }
}
