package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

/**
 * Html view of a {@link Movie}
 */
public class SingleMovieViewHtml implements IView {

    private Movie movie;

    public SingleMovieViewHtml(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Movie " + movie.getId()))
                .append("<body>\n")
                .append(body())
                .append("</body>\n")
                .append("</html>\n")
                .toString();
    }

    private String body() {
        return new StringBuilder()
                .append("\t<h3>" + movie.getId() + "</h3>\n")
                .append("\t<h1>" + movie.getTitle() + " (" + movie.getReleaseYear() + ")</h1>\n")
                .append("\t<h2>" + movie.getGenre() + "</h2>\n")
                .toString();
    }
}
