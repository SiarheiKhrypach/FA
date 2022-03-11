package by.tut.ssmt.service;

import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.ProductDaoImpl;
import by.tut.ssmt.dao.DAO.UserDaoImpl;
import by.tut.ssmt.service.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.service.dataProcessors.DataProcessorList;
import by.tut.ssmt.service.impl.ProductServiceImpl;
import by.tut.ssmt.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

//    private final UserService userService = newUserService();
//    private final ProductService productService = newProductService();

    private final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    private final Validator validator = new Validator();
    private final DBConnector dbConnector = new DBConnector();
    private final ProductDaoImpl productDaoImpl = new ProductDaoImpl(dbConnector);
    private final ProductService productService = new ProductServiceImpl();
    private final UserDaoImpl userDaoImpl = new UserDaoImpl(dbConnector);
    private final UserService userService = new UserServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public DataProcessorList getDataProcessorList() {
        return dataProcessorList;
    }

    public Validator getValidator() {
        return validator;
    }

//    public DBConnector getDbConnector() {
//        return dbConnector;
//    }

    public ProductDaoImpl getProductDaoImpl() {
        return productDaoImpl;
    }

    public UserDaoImpl getUserDaoImpl() {
        return userDaoImpl;
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }
}
