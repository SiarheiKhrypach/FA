package by.tut.ssmt.dao.DAO;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class AbstractDao {

    protected DBConnector dbConnector;
    protected Connection conn;
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    protected AbstractDao(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    protected Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = dbConnector.loadProperties();
        Connection conn = dbConnector.connectToDb(properties);
        return conn;
    }

    protected ResultSet selectToResultSet(String sqlCommand) throws SQLException, IOException, ClassNotFoundException {
        conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlCommand);
        return resultSet;
    }

    protected PreparedStatement prepareStatement(String sqlCommand) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    protected ResultSet selectToResultSetWhere(String sqlCommand, int id) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
//        try {
        preparedStatement = conn.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
//        } catch (SQLException e) {
//            LOGGER.error("SQLException", e);
//            e.printStackTrace(); //todo remove
//        }
        return resultSet;
    }

    protected void delete(String sqlCommand, String name) throws SQLException, IOException, ClassNotFoundException {
        PreparedStatement preparedStatement = prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
    }
}