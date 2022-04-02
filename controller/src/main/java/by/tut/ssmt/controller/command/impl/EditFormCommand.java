package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditFormCommand extends FormsAccessCommand {

    private static final Logger LOGGER = Logger.getLogger(FormsAccessCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();
    final Validator validator = new Validator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            final String productId = request.getParameter("productId");
            final Product product;
            product = productService.selectOneService(Integer.parseInt(productId));
            validator.isNotNull(product);
            request.setAttribute("product", product);
            super.execute(request, response);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
