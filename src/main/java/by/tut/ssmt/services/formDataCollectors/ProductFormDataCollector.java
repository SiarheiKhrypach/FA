package by.tut.ssmt.services.formDataCollectors;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.exceptions.NegativeNumberException;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.exceptions.ZeroException;

import javax.servlet.http.HttpServletRequest;

public class ProductFormDataCollector extends FormDataCollector {

    public Product collectFormData (HttpServletRequest req) throws NullOrEmptyException, NegativeNumberException, ZeroException {
        final String productName = req.getParameter("productName");
        validator.isNotNullOrEmpty(productName);
        final String omegaThree = req.getParameter("omegaThree");
        validator.isNotNullOrNegativeNumber(omegaThree);
        final String omegaSix = req.getParameter("omegaSix");
        validator.isNotNullOrNegativeNumber(omegaSix);
        final String portion = req.getParameter("portions");
        validator.isNotNullZeroOrNegativeNumber(portion);
        Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
        return product;
    }
}
