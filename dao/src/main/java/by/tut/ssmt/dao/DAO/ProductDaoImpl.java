package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.Product;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDaoImpl extends AbstractDao implements ProductDao{

    private static final String SELECT_FROM_TABLE = "SELECT * FROM products";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM products WHERE product_id=?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO products (product_name, omega_three, omega_six, portion) Values (?, ?, ?, ?)";
    private static final String UPDATE_TABLE = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ?, portion = ? WHERE product_id = ?";
//    private static final String DELETE_FROM_TABLE = "DELETE FROM products WHERE product_id = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM products WHERE product_name = ?";

    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class.getName());
    public ProductDaoImpl(DBConnector dbConnector) {
        super(dbConnector);
    }

    public ArrayList<Product> select() throws DaoException {
        ArrayList<Product> products = new ArrayList<>();
        try (ResultSet resultSet = selectToResultSet(SELECT_FROM_TABLE)) {
            while (resultSet.next()) {
                int productId = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                double omegaThree = resultSet.getDouble(3);
                double omegaSix = resultSet.getDouble(4);
                int portion = resultSet.getInt(5);
                Product product = new Product(productId, productName, omegaThree, omegaSix, portion);
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
//            LOGGER.error("Error: ", e);
//            e.printStackTrace();//todo remove
            throw new DaoException("Error in ProductDAO", e);
        }
//        catch (IOException e) {
//            LOGGER.error("Error: ", e);
//            e.printStackTrace();//todo remove
//        }
        finally {
            try {
                if (conn != null)
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }

        return products;
    }

     public Product selectOne(int productId) throws DaoException {
        Product product = null;
        try (ResultSet resultSet = selectToResultSetWhere(SELECT_FROM_TABLE_WHERE, productId)) {
            if (resultSet.next()) {
                int productID = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                double omegaThree = resultSet.getDouble(3);
                double omegaSix = resultSet.getDouble(4);
                int portion = resultSet.getInt(5);
                product = new Product(productID, productName, omegaThree, omegaSix, portion);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("SQLException caught");//todo remove
            LOGGER.error("Error: ", e);
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
        return product;
    }

    public void insert(Product product) throws DaoException {

        try (PreparedStatement preparedStatement = prepareStatement(INSERT_INTO_TABLE)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DaoException ("Error in ProductDAO", e);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
    }

    public void update(Product product) throws DaoException {

        try (PreparedStatement preparedStatement = prepareStatement(UPDATE_TABLE)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.setInt(5, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DaoException("Error in ProductDAO", e);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error: ", e);
                e.printStackTrace();//todo remove
            }
        }
    }

    public void delete(String productName) throws DaoException {
//    public void delete(int productId) {
        try {
            super.delete(DELETE_FROM_TABLE, productName);
//            super.delete(DELETE_FROM_TABLE, productId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            throw new DaoException();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
//                LOGGER.error("Error: ", e);
//                e.printStackTrace(); //todo remove
                throw new DaoException();
            }
        }
    }
}
