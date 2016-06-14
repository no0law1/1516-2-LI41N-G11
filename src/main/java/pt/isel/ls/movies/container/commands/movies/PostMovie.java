package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Submits a Movie to the database
 */
public class PostMovie extends Command.RedirectViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new PostMovie(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("POST", "/movies", "title=?&releaseYear=?", "Posts a movie to the database");
        }
    };

    public PostMovie(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String doWork(Request request) throws Exception {
        int mid;

        String title = request.getParameter("title");
        int releaseYear = request.getIntParameter("releaseYear");
        String genre = request.getParameter("genre");
        Movie movie = new Movie(title, releaseYear, genre);

        try (Connection connection = dataSource.getConnection()) {
            mid = MovieDAO.submitMovie(connection, movie);
        }

        return new StringBuilder("/movies/").append(mid).toString();
    }
}
