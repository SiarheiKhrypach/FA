package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.services.formDataCollectors.ProductFormDataCollector;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import by.tut.ssmt.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCommand implements Command {

    private boolean productAdded;

    private final ProductFormDataCollector dataCollector = new ProductFormDataCollector();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {

        try {
            final Product product = dataCollector.collectFormData(request);
            productAdded = productService.addService(product);
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please enter valid data");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (productAdded) {
            response.sendRedirect("/main");
        } else {
            request.setAttribute("message", "The list already has product with such name");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}

