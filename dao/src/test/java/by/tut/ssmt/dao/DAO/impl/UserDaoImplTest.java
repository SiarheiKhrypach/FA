package by.tut.ssmt.dao.DAO.impl;


import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UserDaoImplTest {

    private UserDaoImpl userDao = new UserDaoImpl();
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

        userDao.selectUserDao();
    }

    @Test
    public void testDeleteUserDao(String userName) throws DaoException, SQLException {
        List<Object> parameters1 = Arrays.asList(
                testUser.getUserName()
        );
        List<Object> parameters2 = Arrays.asList(
                testUser.getUserName(),
                testUser.getPassword()
        );
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement preparedStatement1 = Mockito.mock(PreparedStatement.class);
        PreparedStatement preparedStatement2 = Mockito.mock(PreparedStatement.class);
        String query1 = "SELECT * FROM users WHERE user_name = ?";
        String query2 = "INSERT INTO users (user_name, password) VALUES (?, ?)";

        Mockito.when(connectionPool.take()).thenReturn(connection);

        Mockito.when(connection.prepareStatement(query1)).thenReturn(preparedStatement1);
        Mockito.when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false);
        Mockito.when(resultSet.getInt(1)).thenReturn(0);
    }

}
