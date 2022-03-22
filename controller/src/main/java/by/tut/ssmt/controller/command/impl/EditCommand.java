package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.services.formDataCollectors.FormDataCollector;
import by.tut.ssmt.controller.services.formDataCollectors.ProductFormDataCollector;
import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.ProductDaoImpl;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.service.dataProcessors.DataProcessorList;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class EditCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCommand.class.getName()) ;
    private ArrayList<Product> products;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDaoImpl productDaoImpl = new ProductDaoImpl(dbConnector);
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    final FormDataCollector dataCollector = new ProductFormDataCollector();
    private boolean productDoesntExist;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
        products = productDaoImpl.select();
        validator.isNotNull(products);

            resetData(request);
            collectProportionForContext(request);
            postToMainPage(request, response);

        } catch (NullOrEmptyException e) {
//            assignAttribute(request);
            request.setAttribute("message", "Please enter valid data");
            request.setAttribute("products", products);
//            collectProportionForContext(request);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (DaoException e) {
            e.printStackTrace(); //todo delete
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!productDoesntExist) {
            request.setAttribute("message", "The list already has product with such name");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void assignAttribute(HttpServletRequest request) throws DaoException {
        products = productDaoImpl.select();
        validator.isNotNull(products);
        request.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(HttpServletRequest request) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        request.setAttribute("proportion", formattedProportion);
    }

    private void resetData(HttpServletRequest request) throws NullOrEmptyException, DaoException {
        Product product = getProduct(request);
        verifyIfExist (product);
        productDaoImpl.update(product);
        assignAttribute(request);
    }

    private void verifyIfExist(Product product) {
        productDoesntExist = true;
        for (int i = 0; i < products.size(); i++) {
            if (product.getProductName().equals(products.get(i).getProductName()) && product.getProductId() != products.get(i).getProductId()) {
                productDoesntExist = false;
            }
        }
    }

//    private void assignAttribute(ServletContext servletContext) {
//        products = productDaoImpl.select();
//        validator.isNotNull(products);
//        servletContext.setAttribute("productsAttribute", products);
//    }

    private Product getProduct(HttpServletRequest request) throws NullOrEmptyException {
        final String productId = request.getParameter("productId").trim();
        validator.isNotNullOrEmpty(productId);
        final Product product = (Product) dataCollector.collectFormData(request);
        product.setProductId(Integer.parseInt(productId));
        return product;
    }
}
