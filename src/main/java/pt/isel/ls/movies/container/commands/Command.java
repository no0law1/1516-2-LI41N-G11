package pt.isel.ls.movies.container.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Commentary.
 */
public abstract class Command implements ICommand {

    private String commandRoute;

    public Command(String commandRoute){
        this.commandRoute = commandRoute;
    }

    public String getCommandRoute() {
        return commandRoute;
    }

    protected Map<String, String> getBinds(String path){
        Map<String, String> map = new HashMap<>();
        String[] routeArr = commandRoute.split("/");
        String[] pathArr = path.split("/");

        for (int i = 0; i < routeArr.length; i++) {
            if(routeArr[i].startsWith("{") && routeArr[i].endsWith("}")){
                map.put(routeArr[i].substring(1, routeArr[i].length() - 1), pathArr[i]);
            }
        }
        return map;
    }
}
