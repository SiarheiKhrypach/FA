package by.tut.ssmt.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final String DATABASE_CONFIG_PATH = "db.properties";

    public Properties loadProperties() throws IOException {
        InputStream is = ProductDao.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH));
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }


}
