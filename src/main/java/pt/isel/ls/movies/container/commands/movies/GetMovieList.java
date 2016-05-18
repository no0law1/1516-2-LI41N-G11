package pt.isel.ls.movies.container.commands.movies;

import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.movies.container.commands.Command;
import pt.isel.ls.movies.data.DataSourceFactory;
import pt.isel.ls.movies.data.entity.MovieDAO;
import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.movie.MoviesView;
import pt.isel.ls.movies.view.movie.MoviesViewHtml;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Gets all movie instances from the database
 */
public class GetMovieList extends Command{

    public GetMovieList(DataSource dataSource) {
        super(dataSource);
    }

    private Movie.Sort wrapSortParameter(String sortBy){
        if(sortBy == null) return null;
        switch(sortBy){
            case "addedData": return Movie.Sort.ADDED_DATE;
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
    public void execute(Request request) throws Exception {
        Response response = Response.create(request.getHeader("file-name"));
        List<Movie> movies;

        try (Connection connection = dataSource.getConnection()) {
            movies = MovieDAO.getMovies(
                    connection,
                    Integer.valueOf(request.getParameterOrDefault("top", "-1")),
                    Integer.valueOf(request.getParameterOrDefault("skip", "0")),
                    wrapSortParameter(request.getParameterOrDefault("sortBy", null))
            );
        }

        views.put("text/html", new MoviesViewHtml(movies));
        views.put("text/plain", new MoviesView(movies));

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.write(getView(request.getHeaderOrDefault("accept", "text/html")));
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Movie> movies;
        try (Connection connection = DataSourceFactory.createInstance().getConnection()) {

            movies = MovieDAO.getMovies(
                    connection,
                    Integer.valueOf(req.getParameterMap().getOrDefault("top", new String[]{"-1"})[0]),
                    Integer.valueOf(req.getParameterMap().getOrDefault("skip", new String[]{"0"})[0]),
                    wrapSortParameter(req.getParameter("sortBy"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO handle exception
            return;
        } catch (Exception e) {
            e.printStackTrace();
            //TODO handle exception
            return;
        }

        views.put("text/html", new MoviesViewHtml(movies));
        views.put("text/plain", new MoviesView(movies));

        String contentType = null;
        try {
            contentType = getAcceptType(req, "text/html");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO handle exception
            return;
        }

        Charset utf8 = Charset.forName("utf-8");

        resp.setStatus(200);
        resp.setContentType(String.format("%s; charset=%s", contentType, utf8.name()));
        String content = getView(contentType);
        resp.setContentLength(content.length());
        OutputStream os = resp.getOutputStream();
        os.write(content.getBytes(utf8));
        os.close();
    }
}
