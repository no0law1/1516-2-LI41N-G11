package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

import java.util.List;

/**
 * TODO: Commentary.
 */
public class MoviesViewHtml implements IView {

    private List<Movie> movies;

    public MoviesViewHtml(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Movies"))
                .append(body())
                .append("</html>\n")
                .toString();
    }

    private String body() {
        StringBuilder builder = new StringBuilder();
        builder.append("<table style=\"width:50%\" border=\"5\">\n");
        builder.append("<tr>\n<th>ID</th>\n<th>Title</th>\n<th>Release Year</th>\n<th>Genre</th>\n</tr>\n");
        movies.forEach(
                movie -> builder.append("<tr>\n")
                        .append("\t<td>" + movie.getId() + "</td>\n")
                        .append("\t<td>" + movie.getTitle() + "</td>\n")
                        .append("\t<td>" + movie.getReleaseYear() + "</td>\n")
                        .append("\t<td>" + movie.getGenre() + "</td>\n")
                        .append("</tr>\n"));
        builder.append("</table>\n");
        return builder.toString();
    }
}
