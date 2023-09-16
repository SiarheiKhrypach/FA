package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.MenuDao;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImplTest {

    @Test
    public void testSelectAllFromMenuDao() throws DaoException, SQLException {
        String query = "SELECT products.product_id, products.product_name,products.omega_three, products.omega_six, menu.portions FROM menu, products WHERE menu.user_name = ? and menu.product_id = products.product_id";
        String testUser = "testUser";
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        List<Product> testProducts = Mockito.mock(ArrayList.class);

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(resultSet.getInt(1)).thenReturn(Mockito.anyInt());
        Mockito.when(resultSet.getString(2)).thenReturn(Mockito.anyString());
        Mockito.when(resultSet.getDouble(3)).thenReturn(Mockito.anyDouble());
        Mockito.when(resultSet.getDouble(4)).thenReturn(Mockito.anyDouble());
        Mockito.when(resultSet.getInt(5)).thenReturn(Mockito.anyInt());
        Mockito.when(testProducts.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getInt(5)))).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        MenuDao testMenuDao = new MenuDaoImpl(connectionPool);
        testMenuDao.selectAllFromMenuDao(testUser);

        Mockito.verify(connectionPool).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet, Mockito.times(2)).getInt(1);
        Mockito.verify(resultSet, Mockito.times(2)).getString(2);
        Mockito.verify(resultSet, Mockito.times(2)).getDouble(3);
        Mockito.verify(resultSet, Mockito.times(2)).getDouble(4);
        Mockito.verify(resultSet, Mockito.times(2)).getInt(5);
        Mockito.verify(connectionPool).retrieve(connection);



    }

    @Test
    public void findMenuPageDao() {
    }

    @Test
    public void insertMenuDao() {
    }

    @Test
    public void deleteMenuDao() {
    }

    @Test
    public void portionChangeMenuDao() {
    }

    @Test
    public void bulkPortionChangeMenuDao() {
    }
}