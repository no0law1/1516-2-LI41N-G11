package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.html.HtmlBootstrap;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a set of {@link Collection}
 */
public class CollectionsViewHtml extends HtmlBootstrap {

    public CollectionsViewHtml(List<Collection> collections) {
        super("Collections",
                h1(text("Collections"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px"),
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("ID")),
                                        th(text("Name")),
                                        th(text("Description")),
                                        th(text(""))
                                )
                        ),
                        getList(collections)
                ).withAttr("class", "table table-striped")
                        .withAttr("style", "width:80%;margin:20px auto;"),
                btnGroupJustified(
                        btnGroup(a("/", "Home")
                                .withAttr("role", "btn").withAttr("class", "btn btn-default"))
                ).withAttr("style", "width:80%;margin:20px auto;")
                        .withAttr("class", "text-left")
        );
    }

    private static Writable getList(List<Collection> collections) {
        HtmlElem div = new HtmlElem("tbody");
        collections.forEach(
                collection -> div.withContent(
                        tr(
                                td(text("Collection " + collection.getId())),
                                td(text(collection.getName())),
                                td(text(collection.getDescription())),
                                td(
                                        btn(a("/collections/" + collection.getId(), "See more"))
                                                .withAttr("class", "btn-link")
                                )
                        ))
        );
        return div;
    }
}
