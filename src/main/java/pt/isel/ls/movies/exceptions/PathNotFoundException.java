package pt.isel.ls.movies.exceptions;

import pt.isel.ls.movies.exceptions.common.HTMLException;

/**
 * Created by rcacheira on 23/05/16.
 */
public class PathNotFoundException extends HTMLException {
    public PathNotFoundException(String message){
        super(404, "Path not Found", message);
    }

    public PathNotFoundException(String message, Throwable throwable){
        super(404, "Path not Found", message, throwable);
    }
}
