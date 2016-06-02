package pt.isel.ls.movies.container.commands;

import pt.isel.ls.movies.engine.Request;
import pt.isel.ls.movies.engine.Response;
import pt.isel.ls.movies.exceptions.InvalidAcceptException;
import pt.isel.ls.utils.common.Writable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that represents a command
 */
public abstract class Command implements ICommand {

    private final String METHOD;
    private final String PATH;

    protected Map<String, Writable> views;
    protected DataSource dataSource;

    public Command(DataSource dataSource, String method, String path) {
        this.dataSource = dataSource;
        views = new HashMap<>();

        this.METHOD = method;
        this.PATH = path;
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
        String acceptHeader = request.getHeaderOrDefault("accept", "text/html");
        for(String acceptType: acceptHeader.split(",")){
            Writable view = views.get(acceptType.split(" ")[0]);
            if(view != null){
                response.setContentType(acceptType);
                view.writeTo(response.getWriter());
                response.getWriter().flush();
                return;
            }
        }
        throw new InvalidAcceptException("");
    }

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
