package pt.isel.ls.movies.exceptions;

/**
 * Created by rcacheira on 23/05/16.
 */
public class PathNotFoundException extends Exception {
    public PathNotFoundException(String message){
        super(message);
    }

    public PathNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
