package pt.isel.ls.movies.data.exceptions;

/**
 * Exception thrown when there is no data from the database.
 */
public class NoDataException extends Exception {

    public NoDataException() {
        super();
    }

    public NoDataException(String s) {
        super(s);
    }

}
