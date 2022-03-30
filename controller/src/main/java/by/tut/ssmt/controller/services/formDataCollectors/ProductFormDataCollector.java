package by.tut.ssmt.controller.services.formDataCollectors;

import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class ProductFormDataCollector extends FormDataCollector {

    public Product collectFormData (HttpServletRequest req) throws NullOrEmptyException {
        final String productName = req.getParameter("productName");
        validator.isNotNullOrEmpty(productName);
        final String omegaThree = req.getParameter("omegaThree");
        validator.isNotNullOrEmpty(omegaThree);
        final String omegaSix = req.getParameter("omegaSix");
        validator.isNotNullOrEmpty(omegaSix);
        final String portion = req.getParameter("portions");
        validator.isNotNullOrEmpty(portion);
        Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
        return product;
    }
}