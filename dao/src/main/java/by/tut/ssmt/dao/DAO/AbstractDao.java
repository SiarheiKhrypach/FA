package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.nonNull;

public abstract class AbstractDao {

    private final ConnectionPool connectionPool;
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    protected AbstractDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected Connection getConnection(final boolean hasAutocommit) throws SQLException, DaoException {
        Connection conn = connectionPool.take();
        conn.setAutoCommit(hasAutocommit);
        return conn;
    }

    protected void retrieve(final Connection connection) {
        connectionPool.retrieve(connection);
    }


    protected void close(final ResultSet... resultSets) throws DaoException {
        try {
            if (nonNull(resultSets)) {
                for (final ResultSet resultSet : resultSets) {
                    if (nonNull(resultSet)) {
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error while closing ResultSet", e);
        }
    }

    protected void close(final PreparedStatement... preparedStatements) throws DaoException {
        try {
            if (nonNull(preparedStatements)) {
                for (final PreparedStatement preparedStatement : preparedStatements) {
                    if (nonNull(preparedStatement)) {
                        preparedStatement.close();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error while closing PrepareStatement", e);
        }
    }

    protected PreparedStatement getPreparedStatement(final String query, final Connection connection,
                                                     final List<Object> parameters) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        setPreparedStatementParameters(preparedStatement, parameters);
        return preparedStatement;
    }

    private void setPreparedStatementParameters(final PreparedStatement preparedStatement,
                                                final List<Object> parameters) throws SQLException {
        for (int i = 0, queryParameterIndex = 1; i < parameters.size(); i++, queryParameterIndex++) {
            final Object parameter = parameters.get(i);
            setPreparedStatementParameters(preparedStatement, queryParameterIndex, parameter);
        }
    }

    private void setPreparedStatementParameters(final PreparedStatement preparedStatement,
                                                final int queryParameterIndex, Object parameter) throws SQLException {
        if (Long.class == parameter.getClass()) {
            preparedStatement.setLong(queryParameterIndex, (Long) parameter);
        } else if (Integer.class == parameter.getClass()) {
            preparedStatement.setInt(queryParameterIndex, (Integer) parameter);
        } else if (Double.class == parameter.getClass()) {
            preparedStatement.setDouble(queryParameterIndex, (Double) parameter);
        } else if (String.class == parameter.getClass()) {
            preparedStatement.setString(queryParameterIndex, (String) parameter);
        }
    }
}