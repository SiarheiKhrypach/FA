package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class ProductFormDataCollector extends FormDataCollector {

    public Product collectFormData (HttpServletRequest req) throws NullOrEmptyException {
        final String productName = req.getParameter("productName");
        controllerValidator.isNotNullOrEmpty(productName);
        final String omegaThree = req.getParameter("omegaThree");
        controllerValidator.isNotNullOrEmpty(omegaThree);
        final String omegaSix = req.getParameter("omegaSix");
        controllerValidator.isNotNullOrEmpty(omegaSix);
        final String portion = req.getParameter("portions");
        controllerValidator.isNotNullOrEmpty(portion);
        Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
        return product;
    }
}
