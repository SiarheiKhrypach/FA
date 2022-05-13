package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.DAO.impl.ConnectionPoolImpl;
import by.tut.ssmt.dao.DAO.impl.ProductDaoImpl;
import by.tut.ssmt.dao.DAO.impl.UserDaoImpl;

public class DaoFactory {

    private static final DBConnector dbConnector = new DBConnector();
    private static final ConnectionPool connectionPool = new ConnectionPoolImpl(dbConnector);
    private static final DaoFactory INSTANCE = new DaoFactory();


    private final ProductDao productDao = new ProductDaoImpl(connectionPool);
    private final UserDao userDao = new UserDaoImpl(connectionPool);

    private DaoFactory() {
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}

