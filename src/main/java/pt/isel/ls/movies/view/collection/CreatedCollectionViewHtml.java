package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

/**
 * Html view of a {@link Collection}
 */
public class CreatedCollectionViewHtml extends Html {

    public CreatedCollectionViewHtml(Collection collection) {
        HtmlElem div = new HtmlElem("div");
        collection.getMovies().forEach(
                movie -> div.withContent(
                        tr(
                                td(a("/movies/" + movie.getId(), "Movie: " + movie.getId())),
                                td(text(movie.getTitle())),
                                td(text(String.valueOf(movie.getReleaseYear()))),
                                td(text(movie.getGenre()))
                        ))
        );

        _content = new HtmlPage("Collection",
                h1(text("Created Collection: " + collection.getId())),
                h2(text(collection.getName())),
                p(text(collection.getDescription())),
                table(tr(
                        th(text("ID")), th(text("Title")), th(text("Release Year")), th(text("Genre"))
                        ), div
                ).withAttr("style", "width:50%").withAttr("border", "5")
        );
    }
}
