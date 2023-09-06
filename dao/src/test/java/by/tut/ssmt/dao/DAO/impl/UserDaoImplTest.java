package by.tut.ssmt.dao.DAO.impl;


import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImplTest {


    @Test
    public void testSelectUserDao() throws DaoException, SQLException {
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement1 = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        String query = "SELECT * FROM users";
        List<User> testUsers = Mockito.mock(ArrayList.class);

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement1);
        Mockito.when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(resultSet.getInt(1)).thenReturn(Mockito.anyInt());
        Mockito.when(resultSet.getString(2)).thenReturn(Mockito.anyString());
        Mockito.when(resultSet.getString(3)).thenReturn(Mockito.anyString());
        Mockito.when(testUsers.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)))).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.selectUserDao();

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement1).executeQuery();
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet, Mockito.times(2)).getInt(1);
        Mockito.verify(resultSet, Mockito.times(2)).getString(2);
        Mockito.verify(resultSet, Mockito.times(2)).getString(3);
        Mockito.verify(connectionPool).retrieve(connection);
    }

    @Test
    public void testFindUserDao() throws DaoException, SQLException {
        User user = new User("testUser", "testPass");
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        String query = "SELECT * FROM users WHERE user_name = BINARY ? AND password = BINARY ?";

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(resultSet.getInt(1)).thenReturn(1000);
        Mockito.when(resultSet.getString(2)).thenReturn("testUser");
        Mockito.when(resultSet.getString(3)).thenReturn("testPass");
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.findUserDao(user);

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet, Mockito.times(1)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getInt(1);
        Mockito.verify(resultSet, Mockito.times(1)).getString(2);
        Mockito.verify(resultSet, Mockito.times(1)).getString(3);
        Mockito.verify(connectionPool).retrieve(connection);

    }

    @Test
    public void testSelectOneUSerDao() throws DaoException, SQLException {
        int testId = 1000;
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        String query = "SELECT * FROM users WHERE user_id = ?";

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(resultSet.getInt(1)).thenReturn(1000);
        Mockito.when(resultSet.getString(2)).thenReturn("testUser");
        Mockito.when(resultSet.getString(3)).thenReturn("testPass");
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.selectOneUserDao(testId);

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet, Mockito.times(1)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getInt(1);
        Mockito.verify(resultSet, Mockito.times(1)).getString(2);
        Mockito.verify(resultSet, Mockito.times(1)).getString(3);
        Mockito.verify(connectionPool).retrieve(connection);
    }

    @Test
    public void testFindUserPageDao() throws DaoException, SQLException {
        Page<User> testUsersPagedRequest = new Page();
        testUsersPagedRequest.setPageNumber(1);
        testUsersPagedRequest.setLimit(5);
        testUsersPagedRequest.setOrderBy("users.user_name ASC");
        testUsersPagedRequest.setFilter("'%'");

        ArrayList<User> testUsers = Mockito.mock(ArrayList.class);
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        String query = "SELECT COUNT(*) FROM users WHERE users.user_name LIKE '%'";
        String query2 = "SELECT user_name FROM users WHERE users.user_name LIKE '%' ORDER BY users.user_name ASC LIMIT ? OFFSET ?";

        Mockito.when(connectionPool.take()).thenReturn(connection);

        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(resultSet.getLong(1)).thenReturn(1L);

        Mockito.when(connection.prepareStatement(query2)).thenReturn(preparedStatement);
        Mockito.when(resultSet.getString(1)).thenReturn(Mockito.anyString());
        Mockito.when(testUsers.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)))).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.findUserPageDao(testUsersPagedRequest);

        Mockito.verify(connectionPool, Mockito.times(2)).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement, Mockito.times(2)).executeQuery();
        Mockito.verify(resultSet, Mockito.times(3)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getLong(1);

        Mockito.verify(connection).prepareStatement(query2);
        Mockito.verify(resultSet, Mockito.times(1)).getString(2);
    }

    @Test
    public void testInsertUserDao() throws DaoException, SQLException {
        User testUser = new User("testUser", "testPass");
        String query = "INSERT INTO users (password, user_name) VALUES (?, ?)";
        String query2 = "SELECT * FROM users WHERE user_name=? AND NOT user_id=?";

        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query2)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.FALSE);

        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.doNothing().when(preparedStatement).setString(1, testUser.getPassword());
        Mockito.doNothing().when(preparedStatement).setString(2, testUser.getUserName());
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.insertUserDao(testUser);

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query2);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet, Mockito.times(1)).next();

        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement).setString(1, testUser.getPassword());
        Mockito.verify(preparedStatement).setString(2, testUser.getUserName());
        Mockito.verify(preparedStatement).executeUpdate();
        Mockito.verify(connectionPool).retrieve(connection);
    }

    @Test
    public void testUpdateUserDao() throws DaoException, SQLException {
        User testUser = new User("testUser", "testPass");
        String query = "UPDATE users SET password = ? WHERE user_name = ?";
        String query2 = "SELECT * FROM users WHERE user_name=? AND NOT user_id=?";

        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query2)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.FALSE);

        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.doNothing().when(preparedStatement).setString(1, testUser.getPassword());
        Mockito.doNothing().when(preparedStatement).setString(2, testUser.getUserName());
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.updateUserDao(testUser);

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query2);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet, Mockito.times(1)).next();

        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement).setString(1, testUser.getPassword());
        Mockito.verify(preparedStatement).setString(2, testUser.getUserName());
        Mockito.verify(preparedStatement).executeUpdate();
        Mockito.verify(connectionPool).retrieve(connection);
    }

    @Test
    public void testDeleteUserDao() throws DaoException, SQLException {
        String testName = "admin";
        String query = "DELETE FROM users WHERE user_name = ?";

        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement1 = Mockito.mock(PreparedStatement.class);

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement1);
        Mockito.doNothing().when(preparedStatement1).setString(1, testName);
        Mockito.when(preparedStatement1.executeUpdate()).thenReturn(1);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        UserDao testUserDao = new UserDaoImpl(connectionPool);
        testUserDao.deleteUserDao(testName);

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement1).setString(1, testName);
        Mockito.verify(preparedStatement1).executeUpdate();
        Mockito.verify(connectionPool).retrieve(connection);
    }
}
