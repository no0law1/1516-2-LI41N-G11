package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

/**
 * Html view of a {@link Collection}
 */
public class SingleCollectionViewHtml extends Html {

    public SingleCollectionViewHtml(Collection collection) {
        HtmlElem div = new HtmlElem("div");
        collection.getMovies().forEach(
                movie -> div.withContent(
                        tr(
                                td(text(String.valueOf(movie.getId()))),
                                td(text(movie.getTitle())),
                                td(text(String.valueOf(movie.getReleaseYear()))),
                                td(text(movie.getGenre()))
                        ))
        );

        _content = new HtmlPage("Collection",
                h1(text("Collection " + collection.getId())),
                h2(text(collection.getName())),
                p(text(collection.getDescription())),
                table(tr(
                        th(text("ID")), th(text("Title")), th(text("Release Year")), th(text("Genre"))
                        ), div
                )
        );
    }
}
