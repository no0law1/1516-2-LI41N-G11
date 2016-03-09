/**
 * Created by Clefos on 09-03-2016.
 */

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB_Connectivity {

    private static Connection connect()throws Exception {

        SQLServerDataSource dataSource = new SQLServerDataSource();
        Connection con;
        Properties prop = new Properties();
        FileInputStream configFile = new FileInputStream("config.properties");

        prop.load(configFile);
        dataSource.setServerName(prop.getProperty("sqlServer"));
        dataSource.setInstanceName(prop.getProperty("instanceName"));
        dataSource.setDatabaseName(prop.getProperty("databaseName"));
        dataSource.setPortNumber(Integer.parseInt(prop.getProperty("portNumber")));
        dataSource.setUser(prop.getProperty("user"));
        dataSource.setPassword(prop.getProperty("password"));
        configFile.close();
        con = dataSource.getConnection();

        return con;
    }

    public static Connection getConnection() {

        Connection ret = null;

        try {
            ret = connect();
        } catch (IOException e){
            System.out.println("Error while accessing the properties from file"+e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("The port number must be a number"+e.getMessage());
        } catch (SQLServerException e) {
            System.out.println("Error while getting a connection from the server using a 'dataSource' instance"+e.getMessage());
        }catch (Exception e) {
            System.out.println("Unknown Error"+e.getMessage());
        } finally {
            return ret;
        }
    }
}
