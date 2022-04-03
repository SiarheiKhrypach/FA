package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl extends AbstractDao implements UserDao{

    private static final String SELECT_FROM_TABLE = "SELECT * FROM users";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE user_name = ? AND password = ?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM users WHERE user_id = ?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO users (user_name, password) VALUES (?, ?)";
    private static final String UPDATE_TABLE = "UPDATE users SET password = ?, WHERE user_name = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM users WHERE user_name = ?";

    public UserDaoImpl(DBConnector dbConnector) {
        super(dbConnector);
    }

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

    public ArrayList<User> select() throws DaoException {
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
            throw new DaoException("Error in UserDao", e);
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
        return users;
    }

    public User find (User user) throws DaoException {
        User userFound = null;
        try (ResultSet resultSet = selectToResultSetWhere(FIND_USER_BY_LOGIN_AND_PASSWORD, user.getUserName(), user.getPassword())) {
        if (resultSet.next()) {
            int userId = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String password = resultSet.getString(3);
            userFound = new User(userId, userName, password);
        }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            LOGGER.error("Error: ", e);
            throw new DaoException("Error in UserDao", e);
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
        return userFound;
    }

    final ResultSet selectToResultSetWhere (String sqlCommand, String userName, String password) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = getConnection();
        ResultSet resultSet = null;
            PreparedStatement preparedStatement = prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public User selectOne(int userId) {
        User user = null;
        try (ResultSet resultSet = selectToResultSetWhere(SELECT_FROM_TABLE_WHERE, userId)) {
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(id, name, password);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            LOGGER.error("Error: ", e);
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
        return user;
    }

    public boolean insert(User user) throws DaoException {
        PreparedStatement preparedStatement;
        try (Connection conn = getConnection()) {
            int result = 0;
            preparedStatement = conn.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, user.getUserName());
            ResultSet resultSet = preparedStatement.executeQuery();
            User userMatch = new User();
            while (resultSet.next()){
                userMatch.setUserId(resultSet.getInt(1));
                userMatch.setName(resultSet.getString(2));
                userMatch.setPassword(resultSet.getString(3));
            }
            if (userMatch.getUserName() == null) {
                preparedStatement = prepareStatement(INSERT_INTO_TABLE);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                result = preparedStatement.executeUpdate();
            }
            return (result != 0);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DaoException("Error in UserDao", e);
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
    }

    public void update(User user) {
        try (PreparedStatement preparedStatement = prepareStatement(UPDATE_TABLE)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            LOGGER.error("Error: ", e);
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
    }

    public void delete(String userName) {
        try {
            super.delete(DELETE_FROM_TABLE, userName);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            LOGGER.error("Error: ", e);
            e.printStackTrace(); //todo remove
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
    }
}