package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.ProductDao;
import by.tut.ssmt.dao.domain.Page;
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

public class ProductDaoImplTest {

    @Test
    public void testSelectAllProductDao() throws DaoException, SQLException {
        String query = "SELECT * FROM products";
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

        ProductDao testProductDao = new ProductDaoImpl(connectionPool);
        testProductDao.selectAllProductDao();

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
    public void testFindProductPageDao() throws DaoException, SQLException {
        String query = "SELECT COUNT(*) FROM products WHERE products.product_name LIKE '%'";
        String query2 = "SELECT * FROM products WHERE products.product_name LIKE '%' ORDER BY products.product_name ASC LIMIT ? OFFSET ?";
        Page<Product> testProductPageRequest = new Page();
        testProductPageRequest.setPageNumber(1);
        testProductPageRequest.setLimit(5);
        testProductPageRequest.setOrderBy("products.product_name ASC");
        testProductPageRequest.setFilter("'%'");

        ArrayList<Product> testProducts = Mockito.mock(ArrayList.class);
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(connectionPool.take()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.when(resultSet.getLong(1)).thenReturn(1L);

        Mockito.when(connection.prepareStatement(query2)).thenReturn(preparedStatement);
        Mockito.when(resultSet.getInt(1)).thenReturn(Mockito.anyInt());
        Mockito.when(testProducts.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getInt(5)))).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieve(connection);

        ProductDao testProductDao = new ProductDaoImpl(connectionPool);
        testProductDao.findProductPageDao(testProductPageRequest);

        Mockito.verify(connectionPool, Mockito.times(2)).take();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement, Mockito.times(2)).executeQuery();
        Mockito.verify(resultSet, Mockito.times(3)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getLong(1);

        Mockito.verify(connection).prepareStatement(query2);
        Mockito.verify(resultSet, Mockito.times(1)).getInt(1);
        Mockito.verify(resultSet, Mockito.times(1)).getString(2);
        Mockito.verify(resultSet, Mockito.times(1)).getDouble(3);
        Mockito.verify(resultSet, Mockito.times(1)).getDouble(4);
        Mockito.verify(resultSet, Mockito.times(1)).getInt(5);


    }

    @Test
    public void testSelectOneProductDao() {
        int testId = 1000;
        String query = "SELECT * FROM products WHERE product_id=?";
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

    }

}