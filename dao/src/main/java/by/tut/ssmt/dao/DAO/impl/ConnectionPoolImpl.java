package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.exception.DaoException;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import static java.util.Objects.nonNull;

public class ConnectionPoolImpl implements ConnectionPool {

    private static final int CONNECTIONS_AMOUNT = 4;
    private final DBConnector dbConnector;
    private final ArrayBlockingQueue<Connection> pool;
    private final ArrayBlockingQueue<Connection> takenConnections;

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolImpl.class.getName());
//    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolImpl.class.getName());


    public ConnectionPoolImpl(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
        pool = new ArrayBlockingQueue<>(CONNECTIONS_AMOUNT);
        takenConnections = new ArrayBlockingQueue<>(CONNECTIONS_AMOUNT);
        try {
            initConnectionPool();
        } catch (DaoException e) {
            LOGGER.error(DaoException.getCause(e));
        }
    }

    private void initConnectionPool() throws DaoException {
        try {
            for (int i = 0; i < CONNECTIONS_AMOUNT; i++) {
                pool.add(dbConnector.connectToDb());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DaoException("Error while starting Connection Pool", e);
        }
    }

    @Override
    public Connection take() throws DaoException {
        Connection connection;
        try {
            connection = pool.take();
        } catch (InterruptedException e) {
            throw new DaoException("Error while performing take()", e);
        }
        takenConnections.add(connection);
        return connection;
    }

    @Override
    public void retrieve(Connection connection) {
        if (nonNull(connection)) {
            takenConnections.remove(connection);
            pool.add(connection);
        } else {
            LOGGER.info("Performing retrieve(Connection connection): Connection = null");
        }
    }
}
