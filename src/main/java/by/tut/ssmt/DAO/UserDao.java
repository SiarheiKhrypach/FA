package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends AbstractDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM users";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM users WHERE user_id+?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO users (user_name, password) VALUES (?, ?)";
    private static final String UPDATE_TABLE = "UPDATE users SET password = ?, WHERE user_name = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM users WHERE user_name = ?";

    public UserDao(DBConnector dbConnector) {
        super(dbConnector);
    }

    public ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        try (ResultSet resultSet = selectToResultSet(SELECT_FROM_TABLE)) {
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User(userId, userName, password);
                users.add(user);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User selectOne(int userId) {
        User user = null;
        try (ResultSet resultSet = selectToResultSetWhere(SELECT_FROM_TABLE_WHERE, userId)) {
            if (resultSet.next()) {
                int productId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(productId, name, password);
            }
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            System.out.println("SQLException caught");
        }
        return user;
    }

    public void insert(User user) {
        try (PreparedStatement preparedStatement = prepareStatement(INSERT_INTO_TABLE)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(User user) {
        try (PreparedStatement preparedStatement = prepareStatement(UPDATE_TABLE)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            System.out.println("SQLException caught");
        }
    }

    public void delete(int userId) {
        try {
            super.delete(DELETE_FROM_TABLE, userId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}