package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;

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
        return null;
    }
}
