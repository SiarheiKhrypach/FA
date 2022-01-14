package by.tut.ssmt.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnector {

    private static final String
            DATABASE_CONFIG_PATH = "db.properties",
            URL = "database.url",
            USERNAME = "database.username",
            PASSWORD = "database.password",
            DRIVER = "database.driver";

    private Properties properties;

    Logger logger = Logger.getLogger("INFO");


    final public Properties loadProperties() throws IOException {
        InputStream is = ProductDao.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH));
        properties = new Properties();
        logger.info("!!!!!!!!!!!!!!!Properties information here");
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
