package pt.isel.ls.movies.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Comment
 */
public class Request {

    private String method;

    private String path;

    private String parameters;

    private Map<String, String> map;

    public Request(String method, String path, String parameters) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
    }

    public static Request create(String[] request){
        if(request.length < 2){
            throw new IllegalArgumentException("Bad request");
        }
        return new Request(request[0], request[1], request.length >= 3 ? request[2] : null);
    }

    public Map<String, String> getQueryParams(){
        if(map == null) {
            map = new HashMap<>();
            if(parameters != null && !parameters.isEmpty()) {
                String[] splittedParameters = parameters.split("&");
                for (String splittedParameter : splittedParameters) {
                    String[] keyValue = splittedParameter.split("=");
                    map.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return map;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}