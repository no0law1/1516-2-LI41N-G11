package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.common.CompositeWritable;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

import java.util.List;

/**
 * Html view of a set of {@link Movie}
 */
public class MoviesViewHtml extends Html {

    public MoviesViewHtml(List<Movie> movies) {
        HtmlElem div = new HtmlElem("div");
        movies.forEach(
                movie -> div.withContent(
                        tr(
                                td(text(String.valueOf(movie.getId()))),
                                td(text(movie.getTitle())),
                                td(text(String.valueOf(movie.getReleaseYear()))),
                                td(text(movie.getGenre()))
                        ))
        );
        HtmlPage page = new HtmlPage("Movies",
                table(tr(
                        th(text("ID")), th(text("Title")), th(text("Release Year")), th(text("Genre"))
                        ), div
                )
        );
        _content = new CompositeWritable(page);
    }

}
