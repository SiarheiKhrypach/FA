package by.tut.ssmt.service;

import by.tut.ssmt.service.dataProcessor.AcidsProportionListImpl;
import by.tut.ssmt.service.dataProcessor.DataProcessorList;
import by.tut.ssmt.service.impl.MenuServiceImpl;
import by.tut.ssmt.service.impl.ProductServiceImpl;
import by.tut.ssmt.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    private final ServiceValidator serviceValidator = new ServiceValidator();
    private final ProductService productService = new ProductServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final MenuService menuService = new MenuServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public DataProcessorList getDataProcessorList() {
        return dataProcessorList;
    }

    public ServiceValidator getServiceValidator() {
        return serviceValidator;
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public MenuService getMenuService() {
        return menuService;
    }
}
