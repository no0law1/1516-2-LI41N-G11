package pt.isel.ls.movies.engine;

import org.eclipse.jetty.server.Server;
import pt.isel.ls.movies.container.commands.ICommand;

import java.util.Collection;

/**
 * Created by rcacheira on 26/05/16.
 */
public class ContextData {
    public boolean dynamic;
    public Server runningServer;
    public Collection<ICommand> commands;
}
