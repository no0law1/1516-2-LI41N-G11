package pt.isel.ls.movies.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents the parameters instructed to a command.
 */
public class Parameter {

    private Map<String, String> parameters;

    public Parameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static Parameter create(String pmt){
        Map<String, String> parameters = new HashMap<>();
        String[] parametersArray = pmt.split("&");
        for (int i = 0; i < parametersArray.length; i++) {
            String[] keyValue = parametersArray[i].split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            parameters.put(key, value);
        }
        if(parameters.isEmpty()){
            return null;
        }
        return new Parameter(parameters);
    }

    public static Parameter create(String path, String pmt){
        Map<String, String> parameters = new HashMap<>();
        String[] parametersArray = pmt.split("&");
        for (int i = 0; i < parametersArray.length; i++) {
            String[] keyValue = parametersArray[i].split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            parameters.put(key, value);
        }
        if(parameters.isEmpty()){
            return null;
        }
        return new Parameter(parameters);
    }

    public String getValue(String key){
        return parameters.get(key);
    }
}
