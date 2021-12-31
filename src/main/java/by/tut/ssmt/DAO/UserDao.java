package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class UserDao extends AbstractDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM users";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM users WHERE user_id+?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO users (user_name, password) VALUES (?, ?)";
    private static final String UPDATE_TABLE = "UPDATE users SET password = ?, WHERE user_name = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM users WHERE user_name = ?";
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public UserDao(DBConnector dbConnector) {
        super(dbConnector);
    }

    public ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet = selectToResultSet(SELECT_FROM_TABLE);

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int userId = 0;
            try {
                userId = resultSet.getInt(1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String userName = null;
            try {
                userName = resultSet.getString(2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String password = null;
            try {
                password = resultSet.getString(3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            User user = new User(userId, userName, password);
            users.add(user);
        }
        return users;
    }

    public User selectOne(int userId) {
        User user = null;
        ResultSet resultSet = selectToResultSetWhere(SELECT_FROM_TABLE_WHERE, userId);
        try {
            if (resultSet.next()) {
                int productId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(productId, name, password);
            }
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        }
        return user;
    }

    public void insert(User user) {
        PreparedStatement preparedStatement = prepareStatement(INSERT_INTO_TABLE);
        try {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            LOGGER.info("username - " + user.getUserName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(User user) {
        PreparedStatement preparedStatement = prepareStatement(UPDATE_TABLE);
        try {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("SQLException caught");
        }
    }

    public void delete(int userId) {
        super.delete(DELETE_FROM_TABLE, userId);
    }
}