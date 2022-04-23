package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class AbstractDao {

    protected DBConnector dbConnector;
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    protected AbstractDao(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    protected Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = dbConnector.loadProperties();
        Connection conn = dbConnector.connectToDb(properties);
        return conn;
    }

    protected void close(ResultSet resultSet, PreparedStatement preparedStatement) throws DaoException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Error closing Resultset", e);
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Error closing PrepareStatement", e);
        }
    }
}