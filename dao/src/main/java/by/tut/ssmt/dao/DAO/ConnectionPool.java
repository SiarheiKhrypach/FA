package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {
    Connection take() throws DaoException;
    void retrieve(final Connection connection);
}
