package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.view.common.IView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that represents a command
 */
public abstract class Command implements ICommand {

    protected Map<String, IView> views;
    protected DataSource dataSource;

    public Command(DataSource dataSource) {
        this.dataSource = dataSource;
        views = new HashMap<>();
    }

    public String getView(String option) {
        return views.get(option).getView();
    }
}
