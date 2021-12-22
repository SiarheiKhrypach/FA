package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class ProductDB {

    private static final Logger LOGGER = Logger.getLogger(ProductDB.class.getName());
    private static final String DATABASE_CONFIG_PATH = "db.properties";
    private static final String
            URL = "database.url",
            USERNAME = "database.username",
            PASSWORD = "database.password",
            DRIVER = "database.driver";

    private static Properties properties;

    public static ArrayList<Product> select() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);
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
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Product selectOne(int id) {
        Product product = null;
//        Properties properties = null;
        try {
            properties = loadProperties();
            Connection conn = connectToDb(properties);
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
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static void insert(Product product) {
        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);
            String sql = "INSERT INTO products (product_name, omega_three, omega_six, portion) Values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void update(Product product) {
        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);
            String sql = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ?, portion = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getOmegaThree());
            preparedStatement.setDouble(3, product.getOmegaSix());
            preparedStatement.setInt(4, product.getPortion());
            preparedStatement.setLong(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        try {
            Properties properties = loadProperties();
            Connection conn = connectToDb(properties);
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() throws IOException {
        try (InputStream is = ProductDB.class.getClassLoader().getResourceAsStream((DATABASE_CONFIG_PATH))) {
            LOGGER.info("InputStream is: " + is);
            properties = new Properties();
            properties.load(is);
            return properties;
        }
    }

    private static Connection connectToDb(Properties properties) throws ClassNotFoundException, SQLException {
        String driverClass = properties.getProperty(DRIVER);
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
