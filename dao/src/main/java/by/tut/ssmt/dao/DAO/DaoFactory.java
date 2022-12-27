package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.DAO.impl.*;

public class DaoFactory {

    private static final DBConnector dbConnector = new DBConnectorImpl();
    private static final ConnectionPool connectionPool = new ConnectionPoolImpl(dbConnector);
    private static final DaoFactory INSTANCE = new DaoFactory();


    private final ProductDao productDao = new ProductDaoImpl(connectionPool);
    private final UserDao userDao = new UserDaoImpl(connectionPool);
    private final MenuDao menuDao = new MenuDaoImpl(connectionPool);

    private DaoFactory() {
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}

