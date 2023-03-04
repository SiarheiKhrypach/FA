package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.AbstractDao;
import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.ProductDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDaoImpl extends AbstractDao implements ProductDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM products";
    private static final String COUNT_ALL = "SELECT COUNT(*) FROM products WHERE products.product_name LIKE %s";
    private static final String FIND_PAGE = "SELECT * FROM products WHERE products.product_name LIKE %s ORDER BY %s LIMIT ? OFFSET ?";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM products WHERE product_id=?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO products (product_name, omega_three, omega_six, portions) Values (?, ?, ?, ?)";
    private static final String UPDATE_TABLE = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ? WHERE product_id = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM products WHERE product_name = ?";
    private static final String DELETE_FROM_TABLES = "DELETE products, menu FROM products INNER JOIN menu ON menu.product_id = products.product_id WHERE product_name = ?";
    private static final String FIND_PRODUCT_BY_NAME = "SELECT * FROM products WHERE product_name=?";
    private static final String FIND_PRODUCT_BY_NAME_IN_MENU = "SELECT * FROM menu INNER JOIN products ON products.product_id = menu.product_id WHERE product_name=?";
    private static final String FIND_PRODUCT_BY_NAME_WITH_DIFFERENT_ID = "SELECT * FROM products WHERE product_name=? AND NOT product_id=?";

    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class.getName());

    public ProductDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    @Override
    public List<Product> selectDao() throws DaoException {
        List<Object> parameters = Collections.emptyList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(SELECT_FROM_TABLE, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            final List<Product> products = addProductsFromResultSet(resultSet);
            return products;
        } catch (SQLException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    public Page<Product> findPageDao(Page<Product> productPagedRequest) throws DaoException {
        int limit = productPagedRequest.getLimit();
        final int offset = (productPagedRequest.getPageNumber() - 1) * productPagedRequest.getLimit();
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
            final  String countFilterQuery = String.format(COUNT_ALL, productPagedRequest.getFilter());
            preparedStatement1 = getPreparedStatement(countFilterQuery, connection, parameters1);
            resultSet1 = preparedStatement1.executeQuery();

            long totalElements = 0L;
            if (resultSet1.next()) {
                totalElements = resultSet1.getLong(1);
            }
            if (!productPagedRequest.getFilter().equals("'%'")) {
                limit = (int) totalElements;
//                limit = (int)productPagedRequest.getTotalElements();
                parameters2.set(0, limit);
            }

            final String findPageOrderedQuery = String.format(FIND_PAGE, productPagedRequest.getFilter(), productPagedRequest.getOrderBy());


            preparedStatement2 = getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet2 = preparedStatement2.executeQuery();
            Page<Product> productPage = getProductPaged(productPagedRequest, totalElements, resultSet2);
//            Page<Product> productPage = getProductPaged(productPagedRequest, resultSet1, resultSet2);
            productPage.setLimit(limit);
            return productPage;
//            return getProductPaged(productPagedRequest, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet1, resultSet2);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }


    }

    @Override
    public Product selectOneDao(int productId) throws DaoException {
        List<Object> parameters = Arrays.asList(
                productId
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;
        try {
            connection = getConnection(true);
            preparedStatement = getPreparedStatement(SELECT_FROM_TABLE_WHERE, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int productID = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                double omegaThree = resultSet.getDouble(3);
                double omegaSix = resultSet.getDouble(4);
                int portion = resultSet.getInt(5);
                product = new Product(productID, productName, omegaThree, omegaSix, portion);
            }
            return product;
        } catch (SQLException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    @Override
    public boolean insertDao(Product product) throws DaoException {
        List<Object> parameters1 = Arrays.asList(product.getProductName());
        List<Object> parameters2 = Arrays.asList(
                product.getProductName(),
                product.getOmegaThree(),
                product.getOmegaSix(),
                product.getPortions()
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_PRODUCT_BY_NAME, connection, parameters1);
            resultSet = preparedStatement1.executeQuery();
            Product productMatch = getProduct(resultSet);

            if (productMatch.getProductName() == null) {
                preparedStatement2 = getPreparedStatement(INSERT_INTO_TABLE, connection, parameters2);
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
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    private Product getProduct(ResultSet resultSet) throws SQLException {
        Product productMatch = new Product();
        if (resultSet.next()) {
            productMatch.setProductId(resultSet.getInt(1));
            productMatch.setProductName(resultSet.getString(2));
            productMatch.setOmegaThree(resultSet.getInt(3));
            productMatch.setOmegaSix(resultSet.getInt(4));
            productMatch.setPortions(resultSet.getInt(5));
        }
        return productMatch;
    }

    @Override
    public boolean updateDao(Product product) throws DaoException {
        List<Object> parameters1 = Arrays.asList(
                product.getProductName(),
                product.getProductId()
        );
        List<Object> parameters2 = Arrays.asList(
                product.getProductName(),
                product.getOmegaThree(),
                product.getOmegaSix(),
                product.getProductId()
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_PRODUCT_BY_NAME_WITH_DIFFERENT_ID, connection,
                    parameters1);
            resultSet = preparedStatement1.executeQuery();
            Product productMatch = getProduct(resultSet);
            if (productMatch.getProductName() == null) {
                preparedStatement2 = getPreparedStatement(UPDATE_TABLE, connection, parameters2);
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
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    @Override
    public boolean deleteDao(String productName) throws DaoException {
        List<Object> parameters = Arrays.asList(
                productName
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {

            int result = 0;
            connection = getConnection(false);
            preparedStatement1 = getPreparedStatement(FIND_PRODUCT_BY_NAME_IN_MENU, connection, parameters);
            resultSet = preparedStatement1.executeQuery();
            Product productMatch = getProduct(resultSet);

            if (productMatch.getProductName() == null) {
                preparedStatement2 = getPreparedStatement(DELETE_FROM_TABLE, connection, parameters);
            } else {
                preparedStatement2 = getPreparedStatement(DELETE_FROM_TABLES, connection, parameters);
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
            throw new DaoException("Error in ProductDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }
}
