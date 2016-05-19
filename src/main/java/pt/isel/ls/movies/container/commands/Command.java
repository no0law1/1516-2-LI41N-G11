package pt.isel.ls.movies.container.commands;

import pt.isel.ls.utils.common.Writable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that represents a command
 */
public abstract class Command implements ICommand {

    protected Map<String, Writable> views;
    protected DataSource dataSource;

    public Command(DataSource dataSource) {
        this.dataSource = dataSource;
        views = new HashMap<>();
    }
}
