package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.domain.Product;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImplTest {

    @Test
    public void testSelectAllFromMenuDao() {
        String query = "SELECT products.product_id, products.product_name,products.omega_three, products.omega_six, menu.portions FROM menu, products WHERE menu.user_name = ? and menu.product_id = products.product_id";
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        List<Product> testProducts = Mockito.mock(ArrayList.class);

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