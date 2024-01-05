package it.unisa.saporidiunisa.utils;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest
{
    @Test
    public void getConnectionTest()
    {
        assertNotNull(Database.getConnection());
    }

    @Test
    public void executeSelectDatabaseTest()
    {
        try (final var connection = Database.getConnection())
        {
            final var preparedStatement = connection.prepareStatement("select database();");
            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                final var value = resultSet.getString(resultSet.getMetaData().getColumnCount());
                assertTrue(value != null && !value.isEmpty());
                System.out.println(value);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
