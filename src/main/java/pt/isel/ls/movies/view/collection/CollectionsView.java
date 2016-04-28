package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;

import java.util.List;

/**
 * Plain text view of a set of {@link Collection}
 */
public class CollectionsView implements IView {

    private List<Collection> collections;

    public CollectionsView(List<Collection> collections) {
        this.collections = collections;
    }

    @Override
    public String getView() {
        StringBuilder builder = new StringBuilder();

        collections.forEach(collection -> builder.append(new SingleCollectionView(collection).getView()));

        return builder.toString();
    }
}
