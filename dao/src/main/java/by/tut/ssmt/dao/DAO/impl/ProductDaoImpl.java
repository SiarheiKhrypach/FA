package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.AbstractDao;
import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.ProductDao;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao implements ProductDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM products";
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

    public List<Product> selectDao() throws DaoException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Product> products = new ArrayList<>();
            conn = getConnection(true);
            preparedStatement = conn.prepareStatement(SELECT_FROM_TABLE);
            resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet, preparedStatement);
            retrieve(conn);
        }
    }

    public Product selectOneDao(int productId) throws DaoException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;
        try {
            conn = getConnection(true);
            preparedStatement = conn.prepareStatement(SELECT_FROM_TABLE_WHERE);
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet, preparedStatement);
            retrieve(conn);
        }
    }

    public boolean insertDao(Product product) throws DaoException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            conn = getConnection(false);
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(FIND_PRODUCT_BY_NAME);
            preparedStatement.setString(1, product.getProductName());
            resultSet = preparedStatement.executeQuery();
            Product productMatch = new Product();
            if (resultSet.next()) {
                productMatch.setProductId(resultSet.getInt(1));
                productMatch.setProductName(resultSet.getString(2));
                productMatch.setOmegaThree(resultSet.getInt(3));
                productMatch.setOmegaSix(resultSet.getInt(4));
                productMatch.setPortion(resultSet.getInt(5));
            }

            if (productMatch.getProductName() == null) {
                preparedStatement = conn.prepareStatement(INSERT_INTO_TABLE);
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setDouble(2, product.getOmegaThree());
                preparedStatement.setDouble(3, product.getOmegaSix());
                preparedStatement.setInt(4, product.getPortion());
                result = preparedStatement.executeUpdate();
            }
            conn.commit();
            return (result != 0);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error rolling back", ex);
            }
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet, preparedStatement);
            retrieve(conn);
        }
    }

    public boolean updateDao(Product product) throws DaoException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            conn = getConnection(false);
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(FIND_PRODUCT_BY_NAME_WITH_DIFFERENT_ID);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getProductId());
            resultSet = preparedStatement.executeQuery();
            Product productMatch = new Product();
            if (resultSet.next()) {
                productMatch.setProductId(resultSet.getInt(1));
                productMatch.setProductName(resultSet.getString(2));
                productMatch.setOmegaThree(resultSet.getInt(3));
                productMatch.setOmegaSix(resultSet.getInt(4));
                productMatch.setPortion(resultSet.getInt(5));
            }
            if (productMatch.getProductName() == null) {
                preparedStatement = conn.prepareStatement(UPDATE_TABLE);
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setDouble(2, product.getOmegaThree());
                preparedStatement.setDouble(3, product.getOmegaSix());
                preparedStatement.setInt(4, product.getPortion());
                preparedStatement.setInt(5, product.getProductId());
                result = preparedStatement.executeUpdate();
            }
            conn.commit();
            return (result != 0);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error rolling back", ex);
            }
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            close(resultSet, preparedStatement);
            retrieve(conn);
        }
    }

    public void deleteDao(String productName) throws DaoException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection(false);
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(DELETE_FROM_TABLE);
            preparedStatement.setString(1, productName);
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error rolling back", ex);
            }
            throw new DaoException("Error in ProductDao", e);
        } finally {
            close(null, preparedStatement);
            retrieve(conn);
        }
    }
}
