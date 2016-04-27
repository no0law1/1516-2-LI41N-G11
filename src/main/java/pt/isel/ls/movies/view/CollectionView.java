package pt.isel.ls.movies.view;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;

/**
 * Class whose instance represents a {@link Collection} that knows how to draw itself.
 */
public class CollectionView implements IView {

    private int id;
    private String name;
    private String description;

    public CollectionView(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CollectionView(Collection collection) {
        this(collection.getId(), collection.getName(), collection.getDescription());
    }


    @Override
    public void show() {
        System.out.printf("%s: %s\n", "id", id);
        System.out.printf("%s: %s\n", "name", name);
        System.out.printf("%s: %s\n", "description", description);
        System.out.println();
    }
}
