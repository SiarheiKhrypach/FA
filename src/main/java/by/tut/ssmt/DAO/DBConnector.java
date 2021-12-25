package by.tut.ssmt.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnector {

    private static final Logger LOGGER = Logger.getLogger(ProductDao.class.getName());
    private static final String
            DATABASE_CONFIG_PATH = "db.properties",
            URL = "database.url",
            USERNAME = "database.username",
            PASSWORD = "database.password",
            DRIVER = "database.driver";

    private Properties properties;


    public Properties loadProperties() throws IOException {
        InputStream is = ProductDao.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH));
        properties = new Properties();
        properties.load(is);
        return properties;
    }

    public Connection connectToDb(Properties properties) throws ClassNotFoundException, SQLException {
        String driverClass = properties.getProperty(DRIVER);
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
