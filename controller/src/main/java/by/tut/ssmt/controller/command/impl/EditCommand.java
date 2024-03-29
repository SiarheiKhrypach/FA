package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.ControllerFactory;
import by.tut.ssmt.controller.ControllerValidator;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.exception.NotEqualOperatorsException;
import by.tut.ssmt.controller.formDataCollector.FormDataCollector;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class EditCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCommand.class.getName());
    private boolean productUpdated;
    private final ControllerFactory controllerFactory = ControllerFactory.getInstance();
    private final FormDataCollector dataCollector = controllerFactory.getProductFormDataCollector();
    private final ControllerValidator controllerValidator = controllerFactory.getControllerValidator();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            final Product product = getProduct(request);
            productUpdated = productService.updateService(product);
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute(MESSAGE, "Please enter valid data");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServiceException | NotEqualOperatorsException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (productUpdated) {
            request.getSession().setAttribute(MESSAGE, "Successful operation");
            response.sendRedirect("/main?" + MESSAGE + "=" + productUpdated);
        } else {
            request.setAttribute(MESSAGE, "The list already has product with such name");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private Product getProduct(HttpServletRequest request) throws NullOrEmptyException, NotEqualOperatorsException {
        final String productId = request.getParameter(PRODUCT_ID).trim();
        controllerValidator.isNotNullOrEmpty(productId);
        final Product product = (Product) dataCollector.collectFormData(request);
        product.setProductId(Integer.parseInt(productId));
        return product;
    }
}
