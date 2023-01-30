package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.AbstractDao;
import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM users";
    private static final String COUNT_ALL_USERS = "SELECT COUNT(*) FROM users";
    public static final String FIND_USER_PAGE = "SELECT user_name FROM users LIMIT ? OFFSET ?";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE BINARY user_name = ? AND BINARY password = ?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM users WHERE user_id = ?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO users (user_name, password) VALUES (?, ?)";
    private static final String UPDATE_TABLE = "UPDATE users SET password = ?, WHERE user_name = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM users WHERE user_name = ?";

    public UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

    public List<User> selectDao() throws DaoException {
        List<Object> parameters = Collections.emptyList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<User> users = new ArrayList<>();
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(SELECT_FROM_TABLE, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User(userId, userName, password);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    public User find(User user) throws DaoException {
        List<Object> parameters = Arrays.asList(
                user.getUserName(),
                user.getPassword()
        );
        User userFound = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                userFound = new User(userId, userName, password);
            }
            return userFound;
        } catch (SQLException e) {
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    public User selectOneDao(int userId) throws DaoException {
        List<Object> parameters = Arrays.asList(
                userId
        );
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(false);
            preparedStatement = getPreparedStatement(SELECT_FROM_TABLE_WHERE, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(id, name, password);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    @Override
    public Page<String> findPageDao(Page<User> usersPagedRequest) throws DaoException {
        final int limit = usersPagedRequest.getLimit();
        final int offset = (usersPagedRequest.getPageNumber() - 1) * usersPagedRequest.getLimit();
        List<Object> parameters1 = Collections.emptyList();
        List<Object> parameters2 = Arrays.asList(
                limit,
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = getConnection(true);
            preparedStatement1 = getPreparedStatement(COUNT_ALL_USERS, connection, parameters1);
            preparedStatement2 = getPreparedStatement(FIND_USER_PAGE, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            return getUserPaged(usersPagedRequest, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet1, resultSet2);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    protected Page<String> getUserPaged(Page<User> usersPagedRequest, ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
        final Page<String> usersPaged = new Page<>();
        long totalElements = 0L;
        if (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<String> rows = addUsersFromResultSet(resultSet2);
        usersPaged.setPageNumber(usersPagedRequest.getPageNumber());
        usersPaged.setLimit(usersPagedRequest.getLimit());
        usersPaged.setTotalElements(totalElements);
        usersPaged.setElements(rows);
        return usersPaged;
    }

    private List<String> addUsersFromResultSet(ResultSet resultSet2) throws SQLException {
        List<String> users = new ArrayList<>();
        while (resultSet2.next()) {
            String userName = resultSet2.getString(1);
            users.add(userName);
        }
        return users;
    }

    public boolean insert(User user) throws DaoException {
        return edit(user, INSERT_INTO_TABLE);
    }

    public boolean update(User user) throws DaoException {
        return edit(user, UPDATE_TABLE);
    }

    public boolean edit(User user, String query) throws DaoException {
        List<Object> parameters1 = Arrays.asList(
                user.getUserName()
        );
        List<Object> parameters2 = Arrays.asList(
                user.getUserName(),
                user.getPassword()
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_USER_BY_LOGIN, connection, parameters1);
            resultSet = preparedStatement1.executeQuery();
            User userMatch = new User();
            if (resultSet.next()) {
                userMatch.setUserId(resultSet.getInt(1));
                userMatch.setName(resultSet.getString(2));
                userMatch.setPassword(resultSet.getString(3));
            }
            if (userMatch.getUserName() == null) {
                preparedStatement2 = getPreparedStatement(query, connection, parameters2);
                result = preparedStatement2.executeUpdate();
            }
            connection.commit();
            return (result != 0);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }
//    public boolean update(User user) throws DaoException {
//        List<Object> parameters1 = Arrays.asList(
//                user.getUserName()
//        );
//        List<Object> parameters2 = Arrays.asList(
//                user.getPassword(),
//                user.getUserName()
//        );
//        Connection connection = null;
//        PreparedStatement preparedStatement1 = null;
//        PreparedStatement preparedStatement2 = null;
//        ResultSet resultSet = null;
//        try {
//            int result = 0;
//            connection = getConnection(false);
//            preparedStatement1 = getPreparedStatement(FIND_USER_BY_LOGIN, connection, parameters1);
//            resultSet = preparedStatement1.executeQuery();
//            User userMatch = new User();
//            if (resultSet.next()) {
//                userMatch.setUserId(resultSet.getInt(1));
//                userMatch.setName(resultSet.getString(2));
//                userMatch.setPassword(resultSet.getString(3));
//            }
//            if (userMatch.getUserName() == null) {
//                preparedStatement2 = getPreparedStatement(UPDATE_TABLE, connection, parameters2);
//                result = preparedStatement2.executeUpdate();
//            }
//            connection.commit();
//            return (result != 0);
//
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                throw new DaoException("Error while rolling back", ex);
//            }
//            throw new DaoException("Error in UserDao", e);
//        } finally {
//            close(resultSet);
//            close(preparedStatement1, preparedStatement2);
//            retrieve(connection);
//        }
//    }

    public boolean delete(String userName) throws DaoException {
        List<Object> parameters = Arrays.asList(
                userName
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection(false);
            preparedStatement = getPreparedStatement(DELETE_FROM_TABLE, connection, parameters);
            int result = preparedStatement.executeUpdate();
            connection.commit();
            return (result != 0);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(null, preparedStatement);
            retrieve(connection);
        }
    }
}