package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Plain text view of a set of {@link Collection}
 */
public class CollectionsView implements Writable {

    private StringBuilder builder;

    public CollectionsView(List<Collection> collections) {
        builder = new StringBuilder();
        if (collections.isEmpty()) {
            builder.append("There are no Collections\n");
            return;
        }
        collections.forEach(collection -> builder.append(new SingleCollectionView(collection).getView()));

    }

    public String getView() {
        return builder.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
