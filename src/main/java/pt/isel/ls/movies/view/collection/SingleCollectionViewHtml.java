package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.model.Movie;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a {@link Collection}
 */
public class SingleCollectionViewHtml extends HtmlBootstrapWithHomeButton {

    public SingleCollectionViewHtml(Collection collection, List<Movie> movies, int count, int top, int skip) {
        super("Collections",
                h1(text(collection.getName())).withAttr("class", "text-center"),
                p(text(collection.getDescription())).withAttr("style", "width:80%;margin:20px auto;"),
                getList(movies),
                pagingButtons("/collections/" + collection.getId(), null, count, top, skip),
                h2(text("Add a Movie")).withAttr("class", "text-center"),
                form("POST", "/collections/" + collection.getId() + "/movies",
                        formGroup("mid", "Unique identifier", "mid"),
                        div(
                                new HtmlElem("input")
                                        .withAttr("class", "btn btn-default")
                                        .withAttr("type", "submit")
                                        .withAttr("name", "Submit")
                                        .withAttr("style", "margin-left:10px")
                        ).withAttr("class", "col-sm-10 text-right")
                ).withAttr("class", "form-horizontal clearfix"),
                btnGroupJustified(
                        btnGroup(a("/collections?top=5", "Collections")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("class", "text-left")
        );
    }

    private static HtmlElem getList(List<Movie> movies) {
        HtmlElem div = new HtmlElem("tbody");
        movies.forEach(
                movie -> div.withContent(
                        tr(
                                td(text("Movie: " + movie.getId())),
                                td(text(movie.getTitle())),
                                td(text(String.valueOf(movie.getReleaseYear()))),
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
                                th(text("ID")),
                                th(text("Title")),
                                th(text("Release Year")),
                                th(text("Genre")),
                                th(text(""))
                        )
                ),
                div
        ).withAttr("class", "table table-striped");
    }
}
