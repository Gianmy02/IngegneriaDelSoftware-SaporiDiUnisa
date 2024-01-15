package it.unisa.saporidiunisa.utils;

import lombok.val;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database
{
    private final String driver;
    private final String url;
    private final String username;
    private final String password;
    private static Database _instance = null;
    private static final String RESOURCE_DATABASE = "database.properties";
    private static final String PROPERTY_DRIVER = "jdbc.driver";
    private static final String PROPERTY_URL = "jdbc.url";
    private static final String PROPERTY_USERNAME = "jdbc.username";
    private static final String PROPERTY_PASSWORD = "jdbc.password";

    private Database()
    {
        val properties = getProperties();
        this.driver = properties.getProperty(PROPERTY_DRIVER);
        this.url = properties.getProperty(PROPERTY_URL);
        this.username = properties.getProperty(PROPERTY_USERNAME);
        this.password = properties.getProperty(PROPERTY_PASSWORD);
    }

    private Properties getProperties()
    {
        try (val inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_DATABASE))
        {
            val properties = new Properties();
            properties.load(inputStream);
            return properties;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void loadDriver()
    {
        try
        {
            Class.forName(this.driver);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnectionImpl()
    {
        try
        {
            return DriverManager.getConnection(this.url, this.username, this.password);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection()
    {
        if (_instance == null)
        {
            _instance = new Database();
            _instance.loadDriver();
        }

        return _instance.getConnectionImpl();
    }
}
