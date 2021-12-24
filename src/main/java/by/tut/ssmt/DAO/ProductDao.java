package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class ProductDao {

    DBConnector dbConnector;
    Properties properties;

    public ProductDao(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public ArrayList<Product> select() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                double omegaThree = resultSet.getDouble(3);
                double omegaSix = resultSet.getDouble(4);
                int portion = resultSet.getInt(5);
                Product product = new Product(id, productName, omegaThree, omegaSix, portion);
                products.add(product);
            }
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
        return products;
    }

    public Product selectOne(int id) {
        Product product = null;
        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            String sql = "SELECT * FROM products WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int productId = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                double omegaThree = resultSet.getDouble(3);
                double omegaSix = resultSet.getDouble(4);
                int portion = resultSet.getInt(5);
                product = new Product(productId, productName, omegaThree, omegaSix, portion);
            }
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
        return product;
    }

    public void insert(Product product) {
        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            String sql = "INSERT INTO products (product_name, omega_three, omega_six, portion) Values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.executeUpdate();

        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
    }

    public void update(Product product) {
        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            String sql = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ?, portion = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.setLong(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
    }

    public void delete(int id) {
        try {
            properties = dbConnector.loadProperties();
            Connection conn = dbConnector.connectToDb(properties);
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            System.out.println("loadProperties() method caused IOException");
        } catch (SQLException e) {
            System.out.println("SQLException caught");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName method caused ClassNotFoundException");
        }
    }
}
