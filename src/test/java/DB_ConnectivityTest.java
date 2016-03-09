import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by nuno on 09-03-2016.
 */
public class DB_ConnectivityTest {

    @Test
    public void testGetConnection() throws Exception {
        try(Connection connection = DB_Connectivity.getConnection()){

        }
    }
}