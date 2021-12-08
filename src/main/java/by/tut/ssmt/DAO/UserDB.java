package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDB {

    private static String url = "jdbc:mysql://localhost/fa_db";
    private static String username = "root";
    private static String password = "root";

    public static ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
                while(resultSet.next()) {
                    String userName = resultSet.getString(1);
                    String password = resultSet.getString(2);
                    User user = new User(userName, password);
                    users.add(user);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return users;
    }

    public static User selectOne(String userName) {

        User user = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM users WHERE username=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, userName);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        String name = resultSet.getString(1);
                        String password = resultSet.getString(2);
                        user = new User(name, password);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return user;
    }

    public static int insert(User user) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "INSERT INTO users (username, userpassword) Values (?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, user.getUserName());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    public static int update(User user) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "UPDATE users SET userpassword = ? WHERE username = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, user.getPassword());
                    preparedStatement.setString(2, user.getUserName());
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    public static int delete(String userName ) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "DELETE FROM users WHERE username = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, userName);
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
}
