package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import org.apache.log4j.Logger;

public class DBConnectorImpl implements DBConnector {

    private static final String
            DATABASE_CONFIG_PATH = "db.properties",
            URL = "database.url",
            USERNAME = "database.username",
            PASSWORD = "database.password",
            DRIVER = "database.driver";

    private Properties properties;

    private static final Logger LOGGER = LogManager.getLogger(DBConnectorImpl.class.getName());

    public DBConnectorImpl()  {
        try {
            loadProperties();
        } catch (DaoException e) {
            LOGGER.error(DaoException.getCause(e));
        }
    }

    public void loadProperties() throws DaoException {
        try (InputStream is = DBConnectorImpl.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH))) {
        properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new DaoException("Error while loading properties", e);
        }
    }

    final public Connection connectToDb() throws ClassNotFoundException, SQLException {
        String driverClass = properties.getProperty(DRIVER);
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
