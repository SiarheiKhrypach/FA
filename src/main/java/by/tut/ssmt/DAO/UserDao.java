package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class UserDao {

    private DBConnector dbConnector;
    private Properties properties;

    public UserDao(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String userName = resultSet.getString(1);
                String password = resultSet.getString(2);
                User user = new User(userName, password);
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");        }
        return users;
    }

    public User selectOne(String userName) {
        User user = null;

        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);

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
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
        return user;
    }

    public void insert(User user) {

        try {
            Properties properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);

            String sql = "INSERT INTO users (username, userpassword) Values (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
    }

    public void update(User user) {

        try {
            Properties properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            String sql = "UPDATE users SET userpassword = ? WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
    }


    public void delete(String userName) {

        try {
            Properties properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);

            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
    }
}
