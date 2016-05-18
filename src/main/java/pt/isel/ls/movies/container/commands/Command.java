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
public abstract class Command extends HttpServlet implements ICommand {

    protected Map<String, IView> views;
    protected DataSource dataSource;

    public Command(DataSource dataSource) {
        this.dataSource = dataSource;
        views = new HashMap<>();
    }

    public String getView(String option) {
        return views.get(option).getView();
    }

    //TODO change this method
    public String getAcceptType(HttpServletRequest req, String defaultValue) throws Exception {
        String value = req.getHeader("accept");
        if(value == null){
            return defaultValue;
        }
        for (String acceptType: views.keySet()) {
            if(value.contains(acceptType)){
                return acceptType;
            }
        }
        //TODO change this exception
        throw new Exception("Not supported acceptType");
    }
}
