package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.dataProcessor.DataProcessorList;
import by.tut.ssmt.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.tut.ssmt.controller.util.Util.isNullOrEmpty;

public class DefaultCommand implements Command {

    List<Product> products;

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();
    private final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();

    public static final Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            String currentPageString = request.getParameter("currentPage");
            if (isNullOrEmpty(currentPageString)) {
                currentPageString = "1";
            }
            String currentPageLimit = request.getParameter("pageLimit");
            if (isNullOrEmpty(currentPageLimit)) {
                currentPageLimit = "5";
            }
            int currentPage = Integer.parseInt(currentPageString);
            int pageLimit = Integer.parseInt(currentPageLimit);
            final Page<Product> pagedRequest = new Page<>();
            pagedRequest.setPageNumber(currentPage);
            pagedRequest.setLimit(pageLimit);
            Page<Product> pagedProduct = productService.findPageService(pagedRequest);

            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute("productsPagedAttribute", pagedProduct);


            products = productService.selectAllService();
            LOGGER.info("products - " + products);
            serviceValidator.isNotNull(products);
            servletContext.setAttribute("productsAttribute", products);
            setProportion(request);
            LOGGER.info("products from context - " + request.getServletContext().getAttribute("productsAttribute"));
            servletContext.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServiceException | NullPointerException e) {
//            e.printStackTrace(); //todo delete
            throw new ControllerException(e);
        }
    }

    private void setProportion(HttpServletRequest request) throws NullPointerException {
        final String formattedProportion = dataProcessorList.calculate(products);
        serviceValidator.isNotNull(formattedProportion);
        request.setAttribute("proportion", formattedProportion);
    }
}
