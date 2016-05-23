package pt.isel.ls.movies.exceptions;

/**
 * Exception thrown when there is no data from the database.
 */
public class NoDataException extends PathNotFoundException {

    public NoDataException(String s) {
        super(s);
    }

    public NoDataException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
