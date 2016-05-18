package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Movie;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a Movie to the database
 */
public class PostMovie extends Command {

    public PostMovie(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void execute(Request request, Response response) throws Exception {
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
