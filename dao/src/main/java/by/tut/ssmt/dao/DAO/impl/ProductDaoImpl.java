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
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao implements ProductDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM products";
    private static final String COUNT_ALL = "SELECT COUNT(*) FROM products";
    private static final String FIND_PAGE = "SELECT * FROM products LIMIT ? OFFSET ?";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM products WHERE product_id=?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO products (product_name, omega_three, omega_six, portion) Values (?, ?, ?, ?)";
    private static final String UPDATE_TABLE = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ?, portion = ? WHERE product_id = ?";
    //    private static final String DELETE_FROM_TABLE = "DELETE FROM products WHERE product_id = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM products WHERE product_name = ?";
    private static final String FIND_PRODUCT_BY_NAME = "SELECT * FROM products WHERE product_name=?";
    private static final String FIND_PRODUCT_BY_NAME_WITH_DIFFERENT_ID = "SELECT * FROM products WHERE product_name=? AND NOT product_id=?";

    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class.getName());

    public ProductDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<Product> selectDao() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            List<Product> products = new ArrayList<>();
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(SELECT_FROM_TABLE);
            resultSet = preparedStatement.executeQuery();
            final List <Product> products = addProductsFromResultSet(resultSet);
            return products;
        } catch (SQLException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    private List addProductsFromResultSet(ResultSet resultSet) throws SQLException {
//    private void addProductsFromResultSet(ResultSet resultSet, List<Product> products) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            int productId = resultSet.getInt(1);
            String productName = resultSet.getString(2);
            double omegaThree = resultSet.getDouble(3);
            double omegaSix = resultSet.getDouble(4);
            int portion = resultSet.getInt(5);
            Product product = new Product(productId, productName, omegaThree, omegaSix, portion);
            products.add(product);
        }
        return products;
    }

    public Page<Product> findPageDao(Page<Product> productPagedRequest) throws DaoException {
        final int offset = (productPagedRequest.getPageNumber() - 1) * productPagedRequest.getLimit();
        final int limit = productPagedRequest.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = getConnection(false);
            preparedStatement1 = connection.prepareStatement(COUNT_ALL);
            preparedStatement2 = connection.prepareStatement(FIND_PAGE);
            preparedStatement2.setInt(1, limit);
            preparedStatement2.setInt(2, offset);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            return getProductPaged(productPagedRequest, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet1, resultSet2);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }


    }

    private Page<Product> getProductPaged(Page<Product> productPagedRequest, ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
        final Page<Product> productPaged = new Page<>();
        long totalElements = 0L;
        if (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
//        final List<Product> rows = new ArrayList<>();
        final List<Product> rows = addProductsFromResultSet(resultSet2);
        productPaged.setPageNumber(productPagedRequest.getPageNumber());
        productPaged.setLimit(productPagedRequest.getLimit());
        productPaged.setTotalElements(totalElements);
        productPaged.setElements(rows);
        return productPaged;
    }

    @Override
    public Product selectOneDao(int productId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;
        try {
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(SELECT_FROM_TABLE_WHERE);
            preparedStatement.setInt(1, productId);
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
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            connection.setAutoCommit(false);
            preparedStatement1 = connection.prepareStatement(FIND_PRODUCT_BY_NAME);
            preparedStatement1.setString(1, product.getProductName());
            resultSet = preparedStatement1.executeQuery();
            Product productMatch = new Product();
            if (resultSet.next()) {
                productMatch.setProductId(resultSet.getInt(1));
                productMatch.setProductName(resultSet.getString(2));
                productMatch.setOmegaThree(resultSet.getInt(3));
                productMatch.setOmegaSix(resultSet.getInt(4));
                productMatch.setPortion(resultSet.getInt(5));
            }

            if (productMatch.getProductName() == null) {
                preparedStatement2 = connection.prepareStatement(INSERT_INTO_TABLE);
                preparedStatement2.setString(1, product.getProductName());
                preparedStatement2.setDouble(2, product.getOmegaThree());
                preparedStatement2.setDouble(3, product.getOmegaSix());
                preparedStatement2.setInt(4, product.getPortion());
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
    public boolean updateDao(Product product) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            connection.setAutoCommit(false);
            preparedStatement1 = connection.prepareStatement(FIND_PRODUCT_BY_NAME_WITH_DIFFERENT_ID);
            preparedStatement1.setString(1, product.getProductName());
            preparedStatement1.setInt(2, product.getProductId());
            resultSet = preparedStatement1.executeQuery();
            Product productMatch = new Product();
            if (resultSet.next()) {
                productMatch.setProductId(resultSet.getInt(1));
                productMatch.setProductName(resultSet.getString(2));
                productMatch.setOmegaThree(resultSet.getInt(3));
                productMatch.setOmegaSix(resultSet.getInt(4));
                productMatch.setPortion(resultSet.getInt(5));
            }
            if (productMatch.getProductName() == null) {
                preparedStatement2 = connection.prepareStatement(UPDATE_TABLE);
                preparedStatement2.setString(1, product.getProductName());
                preparedStatement2.setDouble(2, product.getOmegaThree());
                preparedStatement2.setDouble(3, product.getOmegaSix());
                preparedStatement2.setInt(4, product.getPortion());
                preparedStatement2.setInt(5, product.getProductId());
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
    public void deleteDao(String productName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection(false);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_FROM_TABLE);
            preparedStatement.setString(1, productName);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in ProductDao", e);
        } finally {
            close(null, preparedStatement);
            retrieve(connection);
        }
    }
}
