package pt.isel.ls.movies.container.commands.movies;

import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.MoviesView;
import pt.isel.ls.movies.view.movie.MoviesViewHtml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Gets all movie instances from the database
 */
public class GetMovieList extends Command.ViewCommand {

    public static Creator CREATOR = new Creator() {
        @Override
        public Command create(DataSource dataSource) {
            return new GetMovieList(dataSource);
        }

        @Override
        public CommandDetails details() {
            return new CommandDetails("GET", "/movies", null, "Gets all the movies");
        }
    };

    public GetMovieList(DataSource dataSource) {
        super(dataSource);
    }

    private Movie.Sort wrapSortParameter(String sortBy){
        if(sortBy == null) return null;
        switch(sortBy){
            case "addedDate": return Movie.Sort.ADDED_DATE;
            case "addedDateDesc": return Movie.Sort.ADDED_DATE_DESC;
            case "year": return Movie.Sort.YEAR;
            case "yearDesc": return Movie.Sort.YEAR_DESC;
            case "title": return Movie.Sort.TITLE;
            case "titleDesc": return Movie.Sort.TITLE_DESC;
            case "rating": return Movie.Sort.RATING;
            case "ratingDesc": return Movie.Sort.RATING_DESC;
            default: throw new IllegalArgumentException("sortBy value not recognized");
        }
    }

    @Override
    public void doWork(Request request) throws Exception {
        List<Movie> movies;

        int top = Integer.valueOf(request.getParameterOrDefault("top", "-1"));
        int skip = Integer.valueOf(request.getParameterOrDefault("skip", "0"));

        String sortBy = request.getParameterOrDefault("sortBy", null);

        int count;
        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getMovies(
                    connection,
                    top,
                    skip,
                    wrapSortParameter(sortBy)
            );
            count = MovieDAO.getCount(connection);
        }

        views.put("text/html", new MoviesViewHtml(movies, count, top, skip, sortBy));
        views.put("text/plain", new MoviesView(movies));
    }
}
