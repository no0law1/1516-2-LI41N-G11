package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.html.HtmlBootstrap;
import pt.isel.ls.utils.html.HtmlElem;

/**
 * Html view of a {@link Collection}
 */
public class SingleCollectionViewHtml extends HtmlBootstrap {

    public SingleCollectionViewHtml(Collection collection) {
        super("Collections",
                h1(text(collection.getName())).withAttr("class", "text-center"),
                p(text(collection.getDescription())).withAttr("style", "width:80%;margin:20px auto;"),
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
                        getList(collection)
                ).withAttr("class", "table table-striped")
                        .withAttr("style", "width:80%;margin:20px auto;"),
                btnGroupJustified(
                        btnGroup(a("/collections", "Collections")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("style", "width:80%;margin:20px auto;")
                        .withAttr("class", "text-left")
        );
    }

    private static HtmlElem getList(Collection collection) {
        HtmlElem div = new HtmlElem("tbody");
        collection.getMovies().forEach(
                movie -> div.withContent(
                        tr(
                                td(text("Movie: " + movie.getId())),
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
