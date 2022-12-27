package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.AbstractDao;
import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.MenuDao;
import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuDaoImpl extends AbstractDao implements MenuDao {

    private static final String FIND_ITEM_BY_ID = "SELECT * FROM menu WHERE product_id = ?";
    private static final String INSERT_INTO_MENU = "INSERT INTO menu (portions, user_name, product_id) Values (?, ?, ?)";
    private static final String DELETE_FROM_MENU = "DELETE a FROM menu a INNER JOIN products b ON b.product_id = a.product_id AND b.product_name = ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) FROM menu WHERE user_name = ?";
    private static final String FIND_PAGE = "SELECT products.product_id, products.product_name, products.omega_three, products.omega_six, menu.portions FROM menu, products WHERE menu.user_name = ? and menu.product_id = products.product_id LIMIT ? OFFSET ?";
    private static final String ADD_PORTION = "UPDATE menu SET portions = portions + ? WHERE user_name = ? AND product_id = ?";
    private static final String CHANGE_PORTIONS = "UPDATE menu SET portions = ? WHERE user_name = ? AND product_id = ?";

    public MenuDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    @Override
    public Page<Product> findPageDao(Page<Product> menuItemPagedRequest) throws DaoException {
//    public Page<MenuItem> findPageDao(Page<MenuItem> menuItemPagedRequest) throws DaoException {
        final String currentUser = menuItemPagedRequest.getCurrentUser();
//        final int currentUser = menuItemPagedRequest.getCurrentUser();
        final int limit = menuItemPagedRequest.getLimit();
        final int offset = (menuItemPagedRequest.getPageNumber() - 1) * menuItemPagedRequest.getLimit();
        List<Object> parameters1 = Arrays.asList(
                currentUser
        );
        List<Object> parameters2 = Arrays.asList(
                currentUser,
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
            preparedStatement1 = getPreparedStatement(COUNT_ALL, connection, parameters1);
            preparedStatement2 = getPreparedStatement(FIND_PAGE, connection, parameters2);

            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            return getMenuItemPaged(menuItemPagedRequest, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in MenuDao", e);
        } finally {
            close(resultSet1, resultSet2);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    private Page<Product> getMenuItemPaged(Page<Product> menuItemPagedRequest, ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
        final Page<Product> menuItemPaged = new Page<>();
        long totalElements = 0L;
        if (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<Product> rows = addMenuItemsFromResultSet(resultSet2);
        menuItemPaged.setPageNumber(menuItemPagedRequest.getPageNumber());
        menuItemPaged.setLimit(menuItemPagedRequest.getLimit());
        menuItemPaged.setTotalElements(totalElements);
        menuItemPaged.setElements(rows);
        return menuItemPaged;
    }

    private List addMenuItemsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Product> menuItems = new ArrayList<>();
        while (resultSet.next()) {
            int productId = resultSet.getInt(1);
            String productName = resultSet.getString(2);
            double omegaThree = resultSet.getDouble(3);
            double omegaSix = resultSet.getDouble(4);
            int portions = resultSet.getInt(5);
            Product product = new Product(productId, productName, omegaThree, omegaSix, portions);
            menuItems.add(product);
        }
        return menuItems;
    }

    @Override
    public boolean insertDao(MenuItem menuItem) throws DaoException {
        List<Object> parameters1 = Arrays.asList(menuItem.getProductID());
        List<Object> parameters2 = Arrays.asList(
                menuItem.getPortions(),
                menuItem.getUserName(),
                menuItem.getProductID()
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_ITEM_BY_ID, connection, parameters1);
            resultSet = preparedStatement1.executeQuery();
            MenuItem itemMatch = getItem(resultSet);

            if (itemMatch.getProductID() == 0) {
                preparedStatement2 = getPreparedStatement(INSERT_INTO_MENU, connection, parameters2);
            } else {
                preparedStatement2 = getPreparedStatement(ADD_PORTION, connection, parameters2);
            }
            result = preparedStatement2.executeUpdate();
            connection.commit();
            return (result != 0);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in MenuDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    private MenuItem getItem(ResultSet resultSet) throws SQLException {
        MenuItem itemMatch = new MenuItem();
        if (resultSet.next()) {
            itemMatch.setUserName(resultSet.getString(1));
            itemMatch.setProductID(resultSet.getInt(2));
            itemMatch.setPortions(resultSet.getInt(3));
        }
        return itemMatch;
    }

    @Override
    public void deleteDao(String productName) throws DaoException {
        List<Object> parameters = Arrays.asList(
                productName
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection(false);
            preparedStatement = getPreparedStatement(DELETE_FROM_MENU, connection, parameters);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in MenuDao", e);
        } finally {
            close(null, preparedStatement);
            retrieve(connection);
        }
    }

    @Override
    public void portionChangeDao(MenuItem menuItem) throws DaoException {
        List<Object> parameters = Arrays.asList(
                menuItem.getPortions(),
                menuItem.getUserName(),
                menuItem.getProductID()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection(false);
            preparedStatement = getPreparedStatement(CHANGE_PORTIONS, connection, parameters);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in MenuDao", e);
        } finally {
            close(null, preparedStatement);
            retrieve(connection);
        }
    }
}
