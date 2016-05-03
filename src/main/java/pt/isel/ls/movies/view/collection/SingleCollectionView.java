package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.common.IView;
import pt.isel.ls.movies.view.movie.MoviesView;

/**
 * Class whose instance represents a {@link Collection} that knows how to draw itself.
 */
public class SingleCollectionView implements IView {

    private Collection collection;

    private StringBuffer view;

    public SingleCollectionView(Collection collection) {
        this.collection = collection;
    }

    @Override
    public String getView() {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "id", collection.getId()));
        view.append(String.format("%s: %s\n", "name", collection.getName()));
        view.append(String.format("%s: %s\n", "description", collection.getDescription()));
        view.append(new MoviesView(collection.getMovies()).getView());
        view.append("\n");
        return view.toString();
    }
}
