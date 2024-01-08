package it.unisa.saporidiunisa.utils;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest
{
    @Test
    public void connectionTest()
    {
        val connection = Database.getConnection();
        assertNotNull(connection);
        assertDoesNotThrow(connection::close);
    }

    @Test
    public void autoCloseableConnectionTest()
    {
        try (val connection = Database.getConnection())
        {
            assertNotNull(connection);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void executeSelectDatabaseTest()
    {
        try (val connection = Database.getConnection())
        {
            val preparedStatement = connection.prepareStatement("select database();");
            val resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());

            val value = resultSet.getString(resultSet.getMetaData().getColumnCount());
            assertNotNull(value);
            assertFalse(value.isEmpty());

            System.out.println(value);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
