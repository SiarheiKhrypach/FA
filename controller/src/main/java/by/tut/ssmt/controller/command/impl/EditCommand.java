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

public class EditCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCommand.class.getName());
    private boolean productUpdated;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();
    final Validator validator = new Validator();
    final FormDataCollector dataCollector = new ProductFormDataCollector();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            Product product = getProduct(request);
            productUpdated = productService.updateService(product);
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please enter valid data");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (productUpdated) {
            response.sendRedirect("/main");
        } else {
            request.setAttribute("message", "The list already has product with such name");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
