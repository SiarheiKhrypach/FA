package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractDao {

    private final ConnectionPool connectionPool;
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    protected AbstractDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected Connection getConnection(final boolean hasAutocommit) throws IOException, SQLException, ClassNotFoundException, DaoException {
        Connection conn = connectionPool.take();
        conn.setAutoCommit(hasAutocommit);
        return conn;
    }

    protected void retrieve(final Connection connection) {
        connectionPool.retrieve(connection);
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