package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.common.Writable;
import pt.isel.ls.utils.html.HtmlBootstrapWithHomeButton;
import pt.isel.ls.utils.html.HtmlElem;

import java.util.List;

/**
 * Html view of a set of {@link Collection}
 */
public class CollectionsViewHtml extends HtmlBootstrapWithHomeButton {

    public CollectionsViewHtml(List<Collection> collections, int count, int top, int skip) {
        super("Collections",
                h1(text("Collections"))
                        .withAttr("class", "text-center")
                        .withAttr("style", "margin-top:20px"),
                getList(collections),
                pagingButtons("/collections", null, count, top, skip),
                h2(text("Collection Creation"))
                        .withAttr("class", "text-center"),
                form("POST", "/collections",
                        formGroup("name", "Name", "name"),
                        formGroup("description", "Description", "description"),
                        div(
                                new HtmlElem("input")
                                        .withAttr("class", "btn btn-default")
                                        .withAttr("type", "submit")
                                        .withAttr("name", "Submit")
                                        .withAttr("style", "margin-left:10px")
                        ).withAttr("class", "col-sm-10 text-right")
                ).withAttr("class", "form-horizontal clearfix")
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
                                        btn(a("/collections/" + collection.getId() + "?top=5", "See more"))
                                                .withAttr("class", "btn-link")
                                )
                        ))
        );
        return table(
                new HtmlElem("thead",
                        tr(
                                th(text("ID")),
                                th(text("Name")),
                                th(text("Description")),
                                th(text(""))
                        )
                ),
                div
        ).withAttr("class", "table table-striped");
    }
}
