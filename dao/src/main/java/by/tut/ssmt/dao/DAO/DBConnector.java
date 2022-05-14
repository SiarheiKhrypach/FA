package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnector {

    void loadProperties() throws DaoException;

    Connection connectToDb() throws ClassNotFoundException, SQLException;

}