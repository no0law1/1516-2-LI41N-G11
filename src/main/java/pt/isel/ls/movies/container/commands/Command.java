package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
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

    /**
     * This function should do the command work and populate the views Map
     *
     * @param request
     */
    public abstract void doWork(Request request) throws Exception;

    @Override
    public void execute(Request request, Response response) throws Exception {
        doWork(request);

        /**  views.put(OptionView.ERROR, new NotFoundView());  **/
        response.setContentType(request.getHeaderOrDefault("accept", "text/html"));
        views.get(response.getContentType()).writeTo(response.getWriter());
    }
}
