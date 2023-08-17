package by.tut.ssmt.dao.DAO.impl;


import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class UserDaoImplTest {

    //    private UserDaoImpl userDao = new UserDaoImpl();
    User testUser = new User("testUserName", "testPassword");

    @Test
    public void testSelectUserDao() throws DaoException {
        final List<User> expectedUsers = Arrays.asList(
                new User(1, "admin", "root"),
                new User(5, "zxvb", "xcbxcb")
        );
        final int expectedLength = expectedUsers.size();

//        List<Object> parameters = Collections.emptyList();
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            List<User> users = new ArrayList<>();
//            connection = getConnection(true);
//            preparedStatement = getPreparedStatement(SELECT_FROM_TABLE, connection, parameters);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int userId = resultSet.getInt(1);
//                String userName = resultSet.getString(2);
//                String password = resultSet.getString(3);
//                User user = new User(userId, userName, password);
//                users.add(user);
//            }
//            return users;
//        } catch (SQLException e) {
//            throw new DaoException("Error in UserDao", e);
//        } finally {
//            close(resultSet);
//            close(preparedStatement);
//            retrieve(connection);
//        }

//        userDao.selectUserDao();
    }

    @Test
    public void testDeleteUserDao() throws DaoException, SQLException {
        String testName = "admin";
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement1 = Mockito.mock(PreparedStatement.class);
        String query = "DELETE FROM users WHERE user_name = ?";

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
