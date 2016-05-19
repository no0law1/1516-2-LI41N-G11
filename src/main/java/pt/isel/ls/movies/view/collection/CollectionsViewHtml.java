package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.common.CompositeWritable;
import pt.isel.ls.utils.html.Html;
import pt.isel.ls.utils.html.HtmlElem;
import pt.isel.ls.utils.html.HtmlPage;

import java.util.List;

/**
 * Html view of a set of {@link Collection}
 */
public class CollectionsViewHtml extends Html {

    public CollectionsViewHtml(List<Collection> collections) {
        HtmlElem div = new HtmlElem("div");
        collections.forEach(
                collection -> div.withContent(
                        tr(
                                td(a("/collections/" + collection.getId(), "Collection: " + collection.getId())),
                                td(text(String.valueOf(collection.getId()))),
                                td(text(collection.getName())),
                                td(text(collection.getDescription()))
                        ))
        );
        HtmlPage page = new HtmlPage("Movies",
                table(tr(
                        th(text("ID")), th(text("Name")), th(text("Description"))
                        ), div
                ).withAttr("style", "width:50%").withAttr("border", "5")
        );
        _content = new CompositeWritable(page);
    }
}
