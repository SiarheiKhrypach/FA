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
import java.util.Arrays;
import java.util.List;

public class MenuDaoImpl extends AbstractDao implements MenuDao {

    private static final String FIND_ITEM_BY_ID_AND_USER_NAME = "SELECT * FROM menu WHERE product_id = ? AND user_name = ?";
    private static final String INSERT_INTO_MENU = "INSERT INTO menu (portions, user_name, product_id) Values (?, ?, ?)";
    private static final String DELETE_FROM_MENU = "DELETE a FROM menu a INNER JOIN products b ON b.product_id = a.product_id AND b.product_name = ? AND a.user_name = ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) FROM menu, products WHERE user_name = ? AND menu.product_id = products.product_id AND products.product_name LIKE %s";
    private static final String SELECT_FROM_MENU_TABLE = "SELECT products.product_id, products.product_name,products.omega_three, products.omega_six, menu.portions FROM menu, products WHERE menu.user_name = ? and menu.product_id = products.product_id";
    private static final String FIND_PAGE = "SELECT products.product_id, products.product_name, products.omega_three, products.omega_six, menu.portions FROM menu, products WHERE menu.user_name = ? AND menu.product_id = products.product_id AND products.product_name LIKE %s ORDER BY %s LIMIT ? OFFSET ?";
    private static final String ADD_PORTION = "UPDATE menu SET portions = portions + ? WHERE user_name = ? AND product_id = ?";
    private static final String CHANGE_PORTIONS = "UPDATE menu SET portions = ? WHERE user_name = ? AND product_id = ?";

    public MenuDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<Product> selectAllFromMenuDao(String currentUser) throws DaoException {
        List<Object> parameters = Arrays.asList(
                currentUser
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(SELECT_FROM_MENU_TABLE, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            final List<Product> products = addProductsFromResultSet(resultSet);
            return products;
        } catch (SQLException e) {
            throw new DaoException("Error in MenuDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    @Override
    public Page<Product> findMenuPageDao(Page<Product> menuItemPagedRequest) throws DaoException {
        final String currentUser = menuItemPagedRequest.getCurrentUser();

        int limit = menuItemPagedRequest.getLimit();
        final int offset = (menuItemPagedRequest.getPageNumber() - 1) * menuItemPagedRequest.getLimit();
        List<Object> parameters1 = Arrays.asList(
                currentUser
        );
        List<Object> parameters2 = Arrays.asList(
                currentUser,
                limit,
                offset
        );
        try {
            long totalElements = getTotalElements(menuItemPagedRequest, parameters1, COUNT_ALL);
            changeParameterSetIfSearched(menuItemPagedRequest, parameters2, totalElements);
            Page<Product> menuItemPage = (Page<Product>) getPaged(menuItemPagedRequest, FIND_PAGE, parameters2);
            menuItemPage.setLimit(limit);
            menuItemPage.setTotalElements(totalElements);
            return menuItemPage;
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in MenuDao", e);
        }
    }

    @Override
    public boolean insertMenuDao(MenuItem menuItem) throws DaoException {
        List<Object> parameters1 = Arrays.asList(
                menuItem.getProductID(),
                menuItem.getUserName()
        );
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
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_ITEM_BY_ID_AND_USER_NAME, connection, parameters1);
            resultSet = preparedStatement1.executeQuery();
            MenuItem itemMatch = getItem(resultSet);

            if (itemMatch.getProductID() == 0) {
                preparedStatement2 = getPreparedStatement(INSERT_INTO_MENU, connection, parameters2);
            } else {
                preparedStatement2 = getPreparedStatement(ADD_PORTION, connection, parameters2);
            }
            int result = preparedStatement2.executeUpdate();
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
    public boolean deleteMenuDao(String productName, String userName) throws DaoException {
        List<Object> parameters = Arrays.asList(
                productName,
                userName
        );
        return executeUpdate(parameters, DELETE_FROM_MENU);
    }

    private boolean executeUpdate(List<Object> parameters, String command) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection(false);
            preparedStatement = getPreparedStatement(command, connection, parameters);
            int result = preparedStatement.executeUpdate();
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
            close(null, preparedStatement);
            retrieve(connection);
        }
    }

    @Override
    public boolean portionChangeMenuDao(MenuItem menuItem) throws DaoException {
        List<Object> parameters = Arrays.asList(
                menuItem.getPortions(),
                menuItem.getUserName(),
                menuItem.getProductID()
        );
        return executeUpdate(parameters, CHANGE_PORTIONS);
    }

    @Override
    public boolean bulkPortionChangeMenuDao(List<MenuItem> menuList) throws DaoException {
        boolean success = true;
        for (MenuItem menuItem : menuList) {
             if (!portionChangeMenuDao(menuItem)) {
                 success = false;
             }
        }
        return success;
    }
}
