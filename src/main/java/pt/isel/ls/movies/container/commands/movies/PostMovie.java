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

    private static final String DETAILS = "Posts a movie to the database";

    private static final String METHOD = "POST";

    private static final String PATH = "/movies";

    int mid;

    public PostMovie(DataSource dataSource) {
        super(dataSource, METHOD, PATH);
    }

    @Override
    public void doWork(Request request) throws Exception {

        String title = request.getParameter("title");
        int releaseYear = request.getIntParameter("releaseYear");
        String genre = request.getParameter("genre");
        Movie movie = new Movie(title, releaseYear, genre);

        try (Connection connection = dataSource.getConnection()) {
            mid = MovieDAO.submitMovie(connection, movie);
        }

        System.out.printf("mid: %d\n", mid);
    }

    //TODO remove execute method and create views
    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);

        response.setContentType("text/html");
        response.addHeader("Location", PATH + "/" + mid);
        response.setStatus(303);
    }
}
