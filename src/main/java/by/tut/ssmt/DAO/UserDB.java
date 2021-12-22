package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class UserDB {

    private static final Logger LOGGER = Logger.getLogger(ProductDB.class.getName());
    private static final String DATABASE_CONFIG_PATH = "db.properties";
    private static final String
            URL = "database.url",
            USERNAME = "database.username",
            PASSWORD = "database.password",
            DRIVER = "database.driver";
    private static Properties properties;


    public static ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String userName = resultSet.getString(1);
                String password = resultSet.getString(2);
                User user = new User(userName, password);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User selectOne(String userName) {
        User user = null;

        try {
            properties = loadProperties();
            Connection conn = connectToDb(properties);

            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(1);
                String password = resultSet.getString(2);
                user = new User(name, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void insert(User user) {

        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);

            String sql = "INSERT INTO users (username, userpassword) Values (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void update(User user) {

        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);
            String sql = "UPDATE users SET userpassword = ? WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void delete(String userName) {

        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);

            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static Properties loadProperties() throws IOException {
        try (InputStream is = ProductDB.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH))) {
            LOGGER.info("InputStream is: " + is);
            properties = new Properties();
            properties.load(is);
            return properties;
        }
    }

    private static Connection connectToDb(Properties properties) throws ClassNotFoundException, SQLException {
        String driverClass = properties.getProperty(DRIVER);
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
