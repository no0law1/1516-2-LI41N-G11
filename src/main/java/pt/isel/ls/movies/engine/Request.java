package pt.isel.ls.movies.engine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Class whose instance represents a request of data.
 */
public class Request implements IRequest {

    private String method;

    private String path;

    private String headers;

    private String parameters;

    private Map<String, String> parametersMap;

    private Map<String, String> headersMap;

    public Request(String method, String path, String headers, String parameters) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParametersMap() throws UnsupportedEncodingException {
        if (parametersMap == null) {
            parametersMap = resolve(parameters, "&", "=");
        }
        return parametersMap;
    }

    public String getParameter(String key) throws UnsupportedEncodingException {
        return getParameterOrDefault(key, null);
    }

    public String getParameterOrDefault(String key, String defaultValue) throws UnsupportedEncodingException {
        if (parametersMap == null) {
            parametersMap = resolve(parameters, "&", "=");
        }
        return parametersMap.getOrDefault(key, defaultValue);
    }

    public String getHeader(String key) throws UnsupportedEncodingException {
        if (headersMap == null) {
            headersMap = resolve(headers, "\\|", ":");
        }
        return headersMap.get(key);
    }

    private static Map<String, String> resolve(String toBeResolved, String sequenceSeparator, String keyValueSeparator)
            throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        if (toBeResolved != null && !toBeResolved.isEmpty()) {
            String[] splitted = toBeResolved.split(sequenceSeparator);
            for (String splittedKeyValue : splitted) {
                String[] keyValue = splittedKeyValue.split(keyValueSeparator);
                map.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
            }
        }
        return map;
    }

    public static Request create(String[] request) {
        if (request.length < 2) {
            throw new IllegalArgumentException("Bad request");
        }
        if (request.length == 2) {
            return new Request(request[0], request[1], null, null);
        }
        if (request.length == 3) {
            if (request[2].contains("=")) {
                return new Request(request[0], request[1], null, request[2]);
            }
            return new Request(request[0], request[1], request[2], null);
        }
        return new Request(request[0], request[1], request[2], request[3]);
    }
}
