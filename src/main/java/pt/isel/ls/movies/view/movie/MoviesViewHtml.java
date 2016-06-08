package pt.isel.ls.movies.view.movie;

import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;
import java.util.Objects;

/**
 * Html view of a set of {@link Movie}
 */
public class MoviesViewHtml extends HtmlBootstrapWithHomeButton {

    public MoviesViewHtml(String titleSuffix, List<Movie> movies, int count, int top, int skip, String sortBy) {
        super("Movies " + titleSuffix,
                h1(text("Movies " + titleSuffix))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px"),
                getList(movies, sortBy),
                pagingButtons("/movies", sortBy != null ? "sortBy="+sortBy : null, count, top, skip),
                h2(text("Movie Creation")).withAttr("class", "text-center"),
                form("POST", "/movies",
                        formGroup("title", "Title", "title"),
                        formGroup("releaseYear", "Release Year", "releaseYear"),
                        div(
                                new HtmlElem("input")
                                        .withAttr("class", "btn btn-default")
                                        .withAttr("type", "submit")
                                        .withAttr("text", "Submit")
                                        .withAttr("style", "margin-left:10px")
                        ).withAttr("class", "col-sm-10 text-right")
                ).withAttr("class", "form-horizontal clearfix"),
                btnGroupJustified(
                        btnGroup( a("/tops/ratings", "Tops").withAttr("role", "btn").withAttr("class", "btn btn-default"))

                ).withAttr("class", "text-left")
        );
    }

    public MoviesViewHtml(List<Movie> movies, int count, int top, int skip, String sortBy) {
        this("", movies, count, top, skip, sortBy);
    }

    private static Writable getList(List<Movie> movies, String sortBy) {
        HtmlElem div = new HtmlElem("tbody");
        movies.forEach(
                movie -> div.withContent(
                        tr(
                                td(text("Movie " + movie.getId())),
                                td(text(movie.getTitle())),
                                td(text(String.valueOf(movie.getReleaseYear()))),
                                td(text(String.format("%.02f", movie.getAverageRating()))),
                                td(text(movie.getGenre())),
                                td(
                                        btn(a("/movies/" + movie.getId() + "?top=5", "See more"))
                                                .withAttr("class", "btn-link")
                                )
                        ))
        );
        return table(
                new HtmlElem("thead",
                        tr(
                                th(a("/movies?top=5&sortBy=" + (Objects.equals(sortBy, "addedDate") ? "addedDateDesc" : "addedDate"), "ID")),
                                th(a("/movies?top=5&sortBy=" + (Objects.equals(sortBy, "title") ? "titleDesc" : "title"), "Title")),
                                th(a("/movies?top=5&sortBy=" + (Objects.equals(sortBy, "year") ? "yearDesc" : "year"), "Release Year")),
                                th(a("/movies?top=5&sortBy=" + (Objects.equals(sortBy, "rating") ? "ratingDesc" : "rating"), "Average Rating")),
                                th(text("Genre")),
                                th(text(""))
                        )
                ),
                div
        ).withAttr("class", "table table-striped");
    }
}
