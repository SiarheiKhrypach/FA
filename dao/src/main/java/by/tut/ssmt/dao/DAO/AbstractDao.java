package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public abstract class AbstractDao {

    private ConnectionPool connectionPool;
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    protected AbstractDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public AbstractDao() {

    }

    protected List addProductsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            int productId = resultSet.getInt(1);
            String productName = resultSet.getString(2);
            double omegaThree = resultSet.getDouble(3);
            double omegaSix = resultSet.getDouble(4);
            int portion = resultSet.getInt(5);
            Product product = new Product(productId, productName, omegaThree, omegaSix, portion);
            products.add(product);
        }
        return products;
    }

    protected List addUsersFromResultSet(ResultSet resultSet) throws SQLException {
        List<String> users = new ArrayList<>();
        while (resultSet.next()) {
            String userName = resultSet.getString(1);
            users.add(userName);
        }
        return users;
    }

    protected Page<?> getPaged(Page<?> pagedRequest, String sqlQuery, List<Object> parameters) throws SQLException, DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(true);
            final String findPageOrderedQuery = String.format(sqlQuery, pagedRequest.getFilter(), pagedRequest.getOrderBy());
            preparedStatement = getPreparedStatement(findPageOrderedQuery, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getObject(1) instanceof Integer) {
                resultSet.beforeFirst();
                final Page<Product> menuItemPaged = new Page<>();
                final List<Product> rows = addProductsFromResultSet(resultSet);
                menuItemPaged.setPageNumber(pagedRequest.getPageNumber());
                menuItemPaged.setElements(rows);
                return menuItemPaged;
            } else {
                resultSet.beforeFirst();
                final Page<User> userPaged = new Page<>();
                final List<User> rows = addUsersFromResultSet(resultSet);
                userPaged.setPageNumber(pagedRequest.getPageNumber());
                userPaged.setElements(rows);
                return userPaged;
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in AbstractDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    protected long getTotalElements(Page<?> pagedRequest, List<Object> parameters, String sqlQuery) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            final String countFilterQuery = String.format(sqlQuery, pagedRequest.getFilter());
            preparedStatement = getPreparedStatement(countFilterQuery, connection, parameters);
            resultSet = preparedStatement.executeQuery();

            long totalElements = 0L;
            if (resultSet.next()) {
                totalElements = resultSet.getLong(1);
            }

            return totalElements;
        } catch (SQLException | DaoException e) {
            throw new DaoException("Error in AbstractDAO", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }

    }

    protected void changeParameterSetIfSearched(Page<Product> pagedRequest, List<Object> parameters, long totalElements) {
        if (!pagedRequest.getFilter().equals("'%'") && parameters.size()==2) {
            int limit = (int) totalElements;
            parameters.set(0, limit);
        }
        if (!pagedRequest.getFilter().equals("'%'") && parameters.size()==3) {
            int limit = (int) totalElements;
            parameters.set(1, limit);
        }
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

    public PreparedStatement getPreparedStatement(final String query, final Connection connection,
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