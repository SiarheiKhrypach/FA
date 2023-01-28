package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class ProductFormDataCollector extends FormDataCollector {

    public Product collectFormData (HttpServletRequest req) throws NullOrEmptyException {
        final String productName = req.getParameter(PRODUCT_NAME);
        controllerValidator.isNotNullOrEmpty(productName);
        final String omegaThree = req.getParameter(OMEGA_THREE);
        controllerValidator.isNotNullOrEmpty(omegaThree);
        final String omegaSix = req.getParameter(OMEGA_SIX);
        controllerValidator.isNotNullOrEmpty(omegaSix);
        Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix));
        return product;
    }
}
