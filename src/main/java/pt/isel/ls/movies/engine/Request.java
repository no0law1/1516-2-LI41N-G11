package pt.isel.ls.movies.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.movies.exceptions.BadParameterFormatException;
import pt.isel.ls.movies.exceptions.NoDataException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Class whose instance represents a request of data.
 */
public class Request implements IRequest {

    private static Logger logger = LoggerFactory.getLogger(Request.class);

    private String method;
    private String path;
    private String headers;
    private String parameters;
    private Map<String, String> parametersMap;
    private Map<String, String> headersMap;
    private ContextData contextData;

    private Request(ContextData contextData, String method, String path) {
        this(contextData, method, path, null, null);
    }

    private Request(ContextData contextData, String method, String path, String headers, String parameters) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.parameters = parameters;
        this.contextData = contextData;
    }

    public ContextData getContextData() {
        return contextData;
    }

    public String getMethod() {
        return method.toLowerCase();
    }

    public String getPath() {
        return path.toLowerCase();
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

    public int getIntParameter(String key) throws BadParameterFormatException, UnsupportedEncodingException {
        try {
            return Integer.parseInt(getParameter(key));
        }
        catch(NumberFormatException e){
            throw new BadParameterFormatException("Bad format for parameter: " + key);
        }
    }

    public String getParameterOrDefault(String key, String defaultValue) throws UnsupportedEncodingException {
        if (parametersMap == null) {
            parametersMap = resolve(parameters, "&", "=");
        }
        return parametersMap.getOrDefault(key.toLowerCase(), defaultValue);
    }

    public String getHeader(String key) throws UnsupportedEncodingException {
        return getHeaderOrDefault(key, null);
    }

    public String getHeaderOrDefault(String key, String defaultValue) throws UnsupportedEncodingException {
        if (headersMap == null) {
            headersMap = resolve(headers, "\\|", ":");
        }
        return headersMap.getOrDefault(key.toLowerCase(), defaultValue);
    }

    private static Map<String, String> resolve(String toBeResolved, String sequenceSeparator, String keyValueSeparator)
            throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        if (toBeResolved != null && !toBeResolved.isEmpty()) {
            String[] splitted = toBeResolved.split(sequenceSeparator);
            for (String splittedKeyValue : splitted) {
                String[] keyValue = splittedKeyValue.split(keyValueSeparator);
                map.put(keyValue[0].toLowerCase(), URLDecoder.decode(keyValue[1], "UTF-8"));
            }
        }
        return map;
    }

    public static Request create(ContextData contextData, String[] request) {
        if (request.length < 2) {
            throw new IllegalArgumentException("Bad request");
        }
        String method = request[0], path = request[1], headers = null, parameters = null;
        if (request.length == 3) {
            if (request[2].contains("=")) {
                parameters = request[2];
            }
            else{
                headers = request[2];
            }
        }
        else if(request.length == 4){
            headers = request[2];
            parameters = request[3];
        }
        Request cRequest = new Request(contextData, method, path, headers, parameters);
        logger.info("Request created from String[]: " + cRequest);
        return cRequest;
    }

    public static Request create(ContextData contextData, HttpServletRequest request){
        Request cRequest = new Request(contextData, request.getMethod(), request.getPathInfo());
        cRequest.headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            cRequest.headersMap.put(headerName.toLowerCase(), request.getHeader(headerName));
        }

        cRequest.parametersMap = new HashMap<>();
        request.getParameterMap().forEach((s, strings) -> {
            cRequest.parametersMap.put(s.toLowerCase(), strings[0]);
        });

        logger.info("Request created from HttpServletRequest: " + cRequest);
        return cRequest;
    }

    @Override
    public String toString() {
        return "Request{ method: "+method+", path: "+path+", headers: "+headers+", parameters: "+parameters+", parametersMap: "+parametersMap+", headersMap: "+headersMap+"}";
    }
}
