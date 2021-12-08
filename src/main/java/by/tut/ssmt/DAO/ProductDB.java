package by.tut.ssmt.DAO;

import by.tut.ssmt.repository.entities.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB {

    private static String url = "jdbc:mysql://localhost/fa_db";
    private static String username = "root";
    private static String password = "root";

    public static ArrayList<Product> select() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
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
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return products;
    }

    public static Product selectOne(int id) {

        Product product = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM products WHERE id=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
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
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return product;
    }

    public static int insert(Product product) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO products (product_name, omega_three, omega_six, portion) Values (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, product.getProductName());
                    preparedStatement.setDouble(2, product.getOmegaThree());
                    preparedStatement.setDouble(3, product.getOmegaSix());
                    preparedStatement.setInt(4, product.getPortion());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int update(Product product) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "UPDATE products SET product_name = ?, omega_three = ?, omega_six = ?, portion = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, product.getProductName());
                    preparedStatement.setDouble(2, product.getOmegaThree());
                    preparedStatement.setDouble(3, product.getOmegaSix());
                    preparedStatement.setInt(4, product.getPortion());
                    preparedStatement.setLong(5, product.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int delete(int id) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "DELETE FROM products WHERE id = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

}
