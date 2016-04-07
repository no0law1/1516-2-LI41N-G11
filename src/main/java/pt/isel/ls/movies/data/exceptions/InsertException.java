package pt.isel.ls.movies.data.exceptions;

/**
 * Exception thrown when the insertion to the database is not committed.
 */
public class InsertException extends Exception {

    public InsertException(String s) {
        super(s);
    }

}
