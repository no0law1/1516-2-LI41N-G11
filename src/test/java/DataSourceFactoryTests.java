import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Created by nuno on 09-03-2016.
 */
public class DataSourceFactoryTests {

    //CREATE TABLE student ( number int primary key, name varchar(128) );

    private DataSource dataSource;

    public DataSourceFactoryTests(){
        dataSource = DataSourceFactory.createInstance();
    }

    @Test
    public void testGetConnection() throws Exception {
        try(Connection conn = dataSource.getConnection()){}
    }

    @Test
    public void testSelect() throws Exception{
        try(Connection conn = dataSource.getConnection()){
            conn.setAutoCommit(false);
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO student (number, name) VALUES (31743, 'Ricardo Cacheira');")){
                assertEquals(1, pstmt.executeUpdate());
            }
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM student")){
                try(ResultSet result = pstmt.executeQuery()){
                    assertTrue(result.next());
                    assertEquals(31743, result.getInt("number"));
                    assertEquals("Ricardo Cacheira", result.getString("name"));
                }
            }
        }
    }

    @Test
    public void testInsert() throws Exception{
        try(Connection conn = dataSource.getConnection()){
            conn.setAutoCommit(false);
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO student (number, name) VALUES (31743, 'Ricardo Cacheira');")){
                assertEquals(1, pstmt.executeUpdate());
            }
        }
    }
}