package by.tut.ssmt.services.formDataCollectors;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class ProductFormDataCollector extends FormDataCollector {

    public Product collectFormData (HttpServletRequest req) throws NullOrEmptyException {
        final String productName = req.getParameter("productName");
        validator.validate(productName);
        final String omegaThree = req.getParameter("omegaThree");
        validator.validate(omegaThree);
        final String omegaSix = req.getParameter("omegaSix");
        validator.validate(omegaSix);
        final String portion = req.getParameter("portions");
        validator.validate(portion);
        Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
        return product;
    }
}
