package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditFormCommand extends FormsAccessCommand {

    private static final Logger LOGGER = Logger.getLogger(FormsAccessCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException  {
        try {
            final String productId = request.getParameter("productId");
            final Product product;
            product = productService.selectOneService(Integer.parseInt(productId));
            serviceValidator.isNotNull(product);
            request.setAttribute("product", product);
            super.execute(request, response);
        } catch (ServiceException | NullPointerException e) {
            throw new ControllerException(e);
        }
    }
}
