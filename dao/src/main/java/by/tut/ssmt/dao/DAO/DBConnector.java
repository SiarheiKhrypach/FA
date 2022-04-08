package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.DAO.impl.ProductDaoImpl;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    private static final String
            DATABASE_CONFIG_PATH = "db.properties",
            URL = "database.url",
            USERNAME = "database.username",
            PASSWORD = "database.password",
            DRIVER = "database.driver";

    private Properties properties;

    private static final Logger LOGGER = Logger.getLogger(DBConnector.class.getName());

    final public Properties loadProperties() throws IOException {
        InputStream is = ProductDaoImpl.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH));
        properties = new Properties();
        LOGGER.info("!!!!!!!!!!!!!!!Properties information can be here");
        properties.load(is);
        return properties;
    }

    final public Connection connectToDb(Properties properties) throws ClassNotFoundException, SQLException {
        String driverClass = properties.getProperty(DRIVER);
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
