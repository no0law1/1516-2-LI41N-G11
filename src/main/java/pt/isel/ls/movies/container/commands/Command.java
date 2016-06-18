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
 *
 * TO be USED a command should define the following field:
 * {@code public static Creator CREATOR = new CREATOR{...}}
 */
public abstract class Command implements ICommand {

    protected Map<String, Writable> views;
    protected DataSource dataSource;

    public Command(DataSource dataSource) {
        this.dataSource = dataSource;
        views = new HashMap<>();
    }

    public static abstract class RedirectViewCommand extends Command {
        public RedirectViewCommand(DataSource dataSource) {
            super(dataSource);
        }

        /**
         * This function should do the command work and populate the views Map
         *
         * @param request
         * @return Redirect Path
         */
        public abstract String doWork(Request request) throws Exception;

        @Override
        public void execute(Request request, Response response) throws Exception {
            String redirectPath = doWork(request);

            response.addHeader("Location", redirectPath);
            response.setStatus(303);
        }
    }

    public static abstract class ViewCommand extends Command {
        public ViewCommand(DataSource dataSource) {
            super(dataSource);
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
                    response.setStatus(200);
                    response.getWriter().flush();
                    return;
                }
            }
            throw new InvalidAcceptException("");
        }
    }

    public interface Creator{

        //TODO: Not necessary. Why use?
        Command create(DataSource dataSource);
        CommandDetails details();
    }

    public static class CommandDetails{
        public final String method;
        public final String path;
        public final String parameters;
        public final String details;

        public CommandDetails(String method, String path, String parameters, String details) {
            if(details == null || method == null || path == null){
                throw new IllegalArgumentException("details, method or path can't be null");
            }
            this.method = method;
            this.path = path;
            this.parameters = parameters;
            this.details = details;
        }

        @Override
        public String toString() {
            return "CommandDetails{ method:" + method + ", path:" + path + ", parameters: " + parameters + ", details:" + details + "}";
        }
    }
}
