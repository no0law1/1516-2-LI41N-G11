package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.utils.HtmlUtils;

/**
 * Html view of a {@link Collection}
 */
public class SingleCollectionViewHtml implements IView {

    private Collection collection;

    public SingleCollectionViewHtml(Collection collection) {
        this.collection = collection;
    }

    @Override
    public String getView() {
        return new StringBuilder()
                .append("<html>\n")
                .append(HtmlUtils.makeHeader("Collection"))
                .append("<body>\n")
                .append(body())
                .append("</body>\n")
                .append("</html>\n")
                .toString();
    }

    private String body() {
        return new StringBuilder()
                .append("\t<h1>Collection " + collection.getId() + "</h1>\n")
                .append("\t<h2>" + collection.getName() + "</h2>\n")
                .append("\t<p>" + collection.getDescription() + "</p>\n")
                .toString();
    }
}
