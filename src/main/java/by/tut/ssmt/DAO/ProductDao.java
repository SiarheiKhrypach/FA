package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao extends AbstractDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM products";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM products WHERE product_id=?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO products (product_name, omega_three, omega_six, portion) Values (?, ?, ?, ?)";
    private static final String UPDATE_TABLE = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ?, portion = ? WHERE product_id = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM products WHERE product_id = ?";

    public ProductDao(DBConnector dbConnector) {
        super(dbConnector);
    }

    public ArrayList<Product> select() {
        ArrayList<Product> products = new ArrayList<Product>();
        ResultSet resultSet = selectToResultSet(SELECT_FROM_TABLE);

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int productId = 0;
            try {
                productId = resultSet.getInt(1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String productName = null;
            try {
                productName = resultSet.getString(2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            double omegaThree = 0;
            try {
                omegaThree = resultSet.getDouble(3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            double omegaSix = 0;
            try {
                omegaSix = resultSet.getDouble(4);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int portion = 0;
            try {
                portion = resultSet.getInt(5);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Product product = new Product(productId, productName, omegaThree, omegaSix, portion);

                products.add(product);
            }
        return products;
    }

    public Product selectOne(int productId) {
        Product product = null;
        ResultSet resultSet = selectToResultSetWhere(SELECT_FROM_TABLE_WHERE, productId);
        try {
            if (resultSet.next()) {
                int productID = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                double omegaThree = resultSet.getDouble(3);
                double omegaSix = resultSet.getDouble(4);
                int portion = resultSet.getInt(5);
                product = new Product(productID, productName, omegaThree, omegaSix, portion);
            }
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        }
        return product;
    }

    public void insert(Product product) {
            PreparedStatement preparedStatement = prepareStatement(INSERT_INTO_TABLE);

        try {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        }
    }

    public void update(Product product) {
        PreparedStatement preparedStatement = prepareStatement(UPDATE_TABLE);

        try {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.setInt(5, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        }
    }

    public void delete(int productId) {
        super.delete(DELETE_FROM_TABLE, productId);
    }
}
