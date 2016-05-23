package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.html.HtmlBootstrap;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a set of {@link Movie}
 */
public class MoviesViewHtml extends HtmlBootstrapWithHomeButton {

    public MoviesViewHtml(List<Movie> movies) {
        super("Movies",
                h1(text("Movies"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px"),
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("ID")),
                                        th(text("Title")),
                                        th(text("Release Year")),
                                        th(text("Genre")),
                                        th(text(""))
                                )
                        ),
                        getList(movies)
                ).withAttr("class", "table table-striped"),
                btnGroupJustified(
                        btnGroup(
                                a("/tops/ratings", "Listing Tops")
                                        .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                                .withAttr("class", "text-left")
                )
        );
    }

    private static Writable getList(List<Movie> movies) {
        HtmlElem div = new HtmlElem("tbody");
        movies.forEach(
                movie -> div.withContent(
                        tr(
                                td(text("Movie " + movie.getId())),
                                td(text(movie.getTitle())),
                                td(text(String.valueOf(movie.getReleaseYear()))),
                                td(text(movie.getGenre())),
                                td(
                                        btn(a("/movies/" + movie.getId(), "See more"))
                                                .withAttr("class", "btn-link")
                                )
                        ))
        );
        return div;
    }
}
