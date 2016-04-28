package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;

/**
 * Class whose instance represents a {@link Collection} that knows how to draw itself.
 */
public class SingleCollectionView implements IView {

    private int id;
    private String name;
    private String description;

    private StringBuffer view;

    public SingleCollectionView(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public SingleCollectionView(Collection collection) {
        this(collection.getId(), collection.getName(), collection.getDescription());
    }

    @Override
    public String getView() {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "id", id));
        view.append(String.format("%s: %s\n", "name", name));
        view.append(String.format("%s: %s\n", "description", description));
        view.append("\n");
        return view.toString();
    }
}
