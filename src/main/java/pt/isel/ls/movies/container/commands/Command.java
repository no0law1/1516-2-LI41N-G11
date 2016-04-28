package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.view.common.IView;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Commentary.
 */
public abstract class Command implements ICommand {

    protected Map<String, IView> views;

    public Command() {
        views = new HashMap<>();
    }

    public IView getView(String option) {
        return views.get(option);
    }
}
