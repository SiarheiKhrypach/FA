package by.tut.ssmt.controller;

import by.tut.ssmt.controller.formDataCollector.BulkMenuFormDataCollector;
import by.tut.ssmt.controller.formDataCollector.MenuFormDataCollector;
import by.tut.ssmt.controller.formDataCollector.ProductFormDataCollector;
import by.tut.ssmt.controller.formDataCollector.UserFormDataCollector;

public class ControllerFactory {

    private static final ControllerFactory INSTANCE = new ControllerFactory();

    private final ControllerValidator controllerValidator = new ControllerValidator();
    private final ProductFormDataCollector productFormDataCollector = new ProductFormDataCollector();
    private final UserFormDataCollector userFormDataCollector = new UserFormDataCollector();
    private final MenuFormDataCollector menuFormDataCollector = new MenuFormDataCollector();
    private final BulkMenuFormDataCollector bulkMenuFormDataCollector = new BulkMenuFormDataCollector();

    private ControllerFactory(){}

    public static ControllerFactory getInstance() { return  INSTANCE; }
    public ControllerValidator getControllerValidator() {
        return controllerValidator;
    }
    public ProductFormDataCollector getProductFormDataCollector() {
        return productFormDataCollector;
    }
    public UserFormDataCollector getUserFormDataCollector() {
        return userFormDataCollector;
    }
    public MenuFormDataCollector getMenuFormDataCollector() {return menuFormDataCollector; }
    public BulkMenuFormDataCollector getBulkMenuFormDataCollector() { return bulkMenuFormDataCollector; }
}
