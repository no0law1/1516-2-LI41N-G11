package pt.isel.ls.movies.view.collection;

import pt.isel.ls.movies.model.Collection;
import pt.isel.ls.movies.view.movie.MoviesView;
import pt.isel.ls.utils.common.Writable;

import java.io.IOException;
import java.io.Writer;

/**
 * Class whose instance represents a {@link Collection} that knows how to draw itself.
 */
public class CreatedCollectionView implements Writable {

    private StringBuffer view;

    public CreatedCollectionView(Collection collection) {
        view = new StringBuffer();
        view.append(String.format("%s: %s\n", "Created Collection", collection.getId()));
        view.append(String.format("%s: %s\n", "name", collection.getName()));
        view.append(String.format("%s: %s\n", "description", collection.getDescription()));
        view.append("\n");
    }

    public String getView() {
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
