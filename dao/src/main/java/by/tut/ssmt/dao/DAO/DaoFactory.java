package by.tut.ssmt.dao.DAO;

public class DaoFactory {

    private static final DaoFactory INSTANCE = new DaoFactory();

    private final DBConnector dbConnector = new DBConnector();

    private final ProductDao productDao;
    private final UserDao userDao;

    private DaoFactory() {
        this.productDao = new ProductDaoImpl(dbConnector);
        this.userDao = new UserDaoImpl(dbConnector);
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

