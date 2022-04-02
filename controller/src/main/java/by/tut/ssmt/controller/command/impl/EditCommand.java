package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.services.formDataCollectors.FormDataCollector;
import by.tut.ssmt.controller.services.formDataCollectors.ProductFormDataCollector;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import by.tut.ssmt.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class EditCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCommand.class.getName());
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();
    private ArrayList<Product> products;
    final Validator validator = new Validator();
    final FormDataCollector dataCollector = new ProductFormDataCollector();
    private boolean productDoesntExist;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            products = (ArrayList<Product>) productService.selectAllService();
            validator.isNotNull(products);
            resetData(request);
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please enter valid data");
            request.setAttribute("products", products);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!productDoesntExist) {
            request.setAttribute("message", "The list already has product with such name");
        }
        response.sendRedirect("/main");
    }

    private void resetData(HttpServletRequest request) throws NullOrEmptyException, ServiceException {
        Product product = getProduct(request);
        verifyIfExist(product);
        productService.updateService(product);
    }

    private void verifyIfExist(Product product) {
        productDoesntExist = true;
        for (int i = 0; i < products.size(); i++) {
            if (product.getProductName().equals(products.get(i).getProductName()) && product.getProductId() != products.get(i).getProductId()) {
                productDoesntExist = false;
            }
        }
    }

    private Product getProduct(HttpServletRequest request) throws NullOrEmptyException {
        final String productId = request.getParameter("productId").trim();
        LOGGER.info("productID - " + productId);
        validator.isNotNullOrEmpty(productId);
        final Product product = (Product) dataCollector.collectFormData(request);
        product.setProductId(Integer.parseInt(productId));
        LOGGER.info("product - " + product);
        return product;
    }
}
