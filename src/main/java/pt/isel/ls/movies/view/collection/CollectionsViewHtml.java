package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

import java.util.List;

/**
 * TODO: Commentary.
 */
public class CollectionsViewHtml implements IView {

    private List<Collection> collections;

    public CollectionsViewHtml(List<Collection> collections) {
        this.collections = collections;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Collections"))
                .append(body())
                .append("</html>\n")
                .toString();
    }

    private String body() {
        StringBuilder builder = new StringBuilder();
        builder.append("<table style=\"width:50%\" border=\"5\">\n");
        builder.append("<tr>\n<th>ID</th>\n<th>Name</th>\n<th>Description</th>\n</tr>\n");
        collections.forEach(
                collection -> builder.append("<tr>\n")
                        .append("\t<td>" + collection.getId() + "</td>\n")
                        .append("\t<td>" + collection.getName() + "</td>\n")
                        .append("\t<td>" + collection.getDescription() + "</td>\n")
                        .append("</tr>\n"));
        builder.append("</table>\n");
        return builder.toString();
    }
}
