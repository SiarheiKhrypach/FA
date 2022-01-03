package by.tut.ssmt.DAO;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class AbstractDao {

    DBConnector dbConnector;
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    AbstractDao(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = dbConnector.loadProperties();
        Connection connection = dbConnector.connectToDb(properties);
        return connection;
    }

    ResultSet selectToResultSet(String sqlCommand) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlCommand);
        return resultSet;
    }

    ResultSet selectToResultSetWhere(String sqlCommand, int id) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(sqlCommand);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    PreparedStatement prepareStatement(String sqlCommand) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    void delete(String sqlCommand, int id) throws SQLException, IOException, ClassNotFoundException {
        PreparedStatement preparedStatement = prepareStatement(sqlCommand);
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}