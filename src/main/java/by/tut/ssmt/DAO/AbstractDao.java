package by.tut.ssmt.DAO;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class AbstractDao {

    DBConnector dbConnector;
//    static String SELECT_FROM_TABLE;


    AbstractDao(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    Connection getConnection() {
        Properties properties = null;
        try {
            properties = dbConnector.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = dbConnector.connectToDb(properties);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    ResultSet selectToResultSet(String sqlCommand) {
//        try {
        Connection conn = getConnection();
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    ResultSet selectToResultSetWhere(String sqlCommand, int id) {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    PreparedStatement prepareStatement (String sqlCommand) {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    void delete (String sqlCommand, int id) {
            PreparedStatement preparedStatement = prepareStatement(sqlCommand);
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}