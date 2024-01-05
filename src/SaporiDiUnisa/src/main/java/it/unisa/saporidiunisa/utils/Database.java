package it.unisa.saporidiunisa.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database
{
    private final String url;
    private final String username;
    private final String password;
    private static Database _instance = null;
    private static final String RESOURCE_DATABASE = "database.properties";
    private static final String PROPERTY_URL = "jdbc.url";
    private static final String PROPERTY_USERNAME = "jdbc.username";
    private static final String PROPERTY_PASSWORD = "jdbc.password";

    private Database()
    {
        final var properties = getProperties();

        this.url = properties.getProperty(PROPERTY_URL);
        this.username = properties.getProperty(PROPERTY_USERNAME);
        this.password = properties.getProperty(PROPERTY_PASSWORD);
    }

    private Properties getProperties()
    {
        try
        {
            final var properties = new Properties();

            try (final var inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_DATABASE))
            {
                properties.load(inputStream);
            }

            return properties;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection()
    {
        if (_instance == null)
            _instance = new Database();

        try
        {
            return DriverManager.getConnection(_instance.url, _instance.username, _instance.password);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
