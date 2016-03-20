package pt.isel.ls.movies.data;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;

/**
 * Class that knows how to create a DataSource
 */
public class DataSourceFactory {

    /**
     * creates a DataSource with the environment variables defined bellow
     * 
     * LS_1516V_DB_SERVER - Database Server name
     * LS_1516V_DB_NAME - Database name
     * LS_1516V_DB_PORT_NUMBER - Database port number
     * LS_1516V_DB_USER - Database user
     * LS_1516V_DB_PASSWORD - Database user password
     *
     * @return DataSource that can be used to connect to the database set on environment variables
     */
    public static DataSource createInstance(){
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();

        pgSimpleDataSource.setServerName(System.getenv("LS_1516V_DB_SERVER"));
        pgSimpleDataSource.setDatabaseName(System.getenv("LS_1516V_DB_NAME"));
        pgSimpleDataSource.setPortNumber(Integer.parseInt(System.getenv("LS_1516V_DB_PORT_NUMBER")));
        pgSimpleDataSource.setUser(System.getenv("LS_1516V_DB_USER"));
        pgSimpleDataSource.setPassword(System.getenv("LS_1516V_DB_PASSWORD"));

        return pgSimpleDataSource;
    }

    /**
     * creates a DataSource with the environment variables defined bellow
     *
     * LS_1516V_DB_SERVER - Database Server name
     * LS_1516V_DB_NAME - Database name
     * LS_1516V_DB_PORT_NUMBER - Database port number
     * LS_1516V_DB_USER - Database user
     * LS_1516V_DB_PASSWORD - Database user password
     *
     * @return DataSource that can be used to connect to the database set on environment variables
     */
    public static DataSource createTestInstance(){
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();

        pgSimpleDataSource.setServerName(System.getenv("LS_1516V_DB_SERVER"));
        pgSimpleDataSource.setDatabaseName(System.getenv("LS_1516V_DB_TESTNAME"));
        pgSimpleDataSource.setPortNumber(Integer.parseInt(System.getenv("LS_1516V_DB_PORT_NUMBER")));
        pgSimpleDataSource.setUser(System.getenv("LS_1516V_DB_USER"));
        pgSimpleDataSource.setPassword(System.getenv("LS_1516V_DB_PASSWORD"));

        return pgSimpleDataSource;
    }
}
