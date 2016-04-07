package pt.isel.ls.movies.data;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test {@link DataSourceFactory}
 */
public class DataSourceFactoryTest {

    //CREATE TABLE student ( number int primary key, name varchar(128) );

    private DataSource dataSource;
    public DataSourceFactoryTest() {
        dataSource = DataSourceFactory.createTestInstance();
    }

    /**
     * Tests the connection to DataBase
     *
     * @throws SQLException if some error occurs
     */
    @Test
    public void testGetConnection() throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            conn.prepareStatement("SELECT * FROM student").executeQuery();
        }
    }

    /**
     * Tests SELECT after INSERT
     * the data used by this test isn't persistent
     *
     * @throws SQLException if some error occurs
     */
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
    /**
     * Tests INSERT
     * the data used by this test isn't persistent
     *
     * @throws SQLException if some error occurs
     */
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

    @Test
    public void testDelete() throws Exception{
        try(Connection con = dataSource.getConnection()){
            con.setAutoCommit(false);
            con.prepareStatement("insert into student(number, name) values (37686, 'Cristian Clefos'), (37687, 'Cristian Clefos'), (37688, 'Cristian')").executeUpdate();
            con.prepareStatement("delete from student where student.name = 'Cristian Clefos'").executeUpdate();
            ResultSet res = con.prepareStatement("select * from student where name = 'Cristian Clefos'").executeQuery();
            assertEquals(false, res.next());
            res = con.prepareStatement("select * from student where name = 'Cristian'").executeQuery();
            res.next();
            assertEquals(res.getString("name"), "Cristian");
        }
    }


}