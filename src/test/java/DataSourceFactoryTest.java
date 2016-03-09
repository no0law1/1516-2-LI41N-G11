import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Commentary
 */
public class DataSourceFactoryTest {

    private DataSource dataSource;

    @Before
    public void setUp(){
        dataSource = DataSourceFactory.createDataSource();
    }

    @After
    public void tearDown(){ }


    @Test
    public void testGetConnection() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.prepareStatement("SELECT * FROM student").executeQuery();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            assertEquals(1, connection.prepareStatement("INSERT INTO student (number, name) values (12345, 'Nuno')").executeUpdate());
            assertEquals(1, connection.prepareStatement("UPDATE student set name='Nuno Reis' where number=12345").executeUpdate());
            ResultSet resultSet = connection.prepareStatement("SELECT name FROM student where number=12345").executeQuery();
            resultSet.next();
            assertEquals(resultSet.getString("name"), "Nuno Reis");
        }
    }
}