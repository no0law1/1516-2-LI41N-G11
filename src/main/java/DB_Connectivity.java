/**
 * Created by Clefos on 09-03-2016.
 */

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

public class DB_Connectivity {

    private static DataSource dataSource;

    public static DataSource getDataSource(){
        if(dataSource == null) {
            PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();

            Properties prop = new Properties();

            pgSimpleDataSource.setServerName(System.getenv("LS_1516V_DB_SERVER"));
            pgSimpleDataSource.setDatabaseName(System.getenv("LS_1516V_DB_NAME"));
            pgSimpleDataSource.setPortNumber(Integer.parseInt(System.getenv("LS_1516V_DB_PORT_NUMBER")));
            pgSimpleDataSource.setUser(System.getenv("LS_1516V_DB_USER"));
            pgSimpleDataSource.setPassword(System.getenv("LS_1516V_DB_PASSWORD"));

            dataSource = pgSimpleDataSource;
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
