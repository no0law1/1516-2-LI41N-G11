package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class PathNotFoundException extends HTMLException {
    public PathNotFoundException(String message){
        super(404, message);
    }

    public PathNotFoundException(String message, Throwable throwable){
        super(404, message, throwable);
    }
}
