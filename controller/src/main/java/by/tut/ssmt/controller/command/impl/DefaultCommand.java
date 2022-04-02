package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.dataProcessors.DataProcessorList;
import by.tut.ssmt.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class DefaultCommand implements Command {

    ArrayList<Product> products;

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();
    private final Validator validator = serviceFactory.getValidator();
    private final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();

    public static final Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            products = (ArrayList<Product>) productService.selectAllService();
            LOGGER.info("products - " + products);
            validator.isNotNull(products);
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute("productsAttribute", products);
            setProportion(request);
            LOGGER.info("products from context - " + request.getServletContext().getAttribute("productsAttribute"));
            servletContext.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace(); //todo delete
            throw new ControllerException(e);
        }
    }

    private void setProportion(HttpServletRequest request) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        request.setAttribute("proportion", formattedProportion);
    }
}
