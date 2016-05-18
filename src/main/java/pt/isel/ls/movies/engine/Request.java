package pt.isel.ls.movies.engine;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
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

    private Request(String method, String path) {
        this.method = method;
        this.path = path;
    }

    private Request(String method, String path, String headers, String parameters) {
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
        return getHeaderOrDefault(key, null);
    }

    public String getHeaderOrDefault(String key, String defaultValue) throws UnsupportedEncodingException {
        if (headersMap == null) {
            headersMap = resolve(headers, "\\|", ":");
        }
        return headersMap.getOrDefault(key, defaultValue);
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
            return new Request(request[0], request[1]);
        }
        if (request.length == 3) {
            if (request[2].contains("=")) {
                return new Request(request[0], request[1], null, request[2]);
            }
            return new Request(request[0], request[1], request[2], null);
        }
        return new Request(request[0], request[1], request[2], request[3]);
    }

    private static String getHeaderOrDefault(HttpServletRequest request, String name, String def){
        String val = request.getHeader(name);
        return val != null ? val : def;
    }

    public static Request create(HttpServletRequest request){
        Request cRequest = new Request(request.getMethod(), request.getPathInfo());
        cRequest.headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            cRequest.headersMap.put(headerName, request.getHeader(headerName));
        }

        cRequest.parametersMap = new HashMap<>();
        request.getParameterMap().forEach((s, strings) -> {
            cRequest.parametersMap.put(s, strings[0]);
        });

        return cRequest;
    }
}
