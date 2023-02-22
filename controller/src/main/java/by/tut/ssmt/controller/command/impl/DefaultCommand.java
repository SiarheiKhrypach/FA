package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tut.ssmt.controller.util.ControllerConstants.*;
import static by.tut.ssmt.controller.util.Util.isNullOrEmpty;

public class DefaultCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();

    public static final Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            String currentPageString = request.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageString)) {
                currentPageString = "1";
            }
            String currentPageLimit = request.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentPageLimit)) {
                currentPageLimit = "5";
            }
            String orderBy = request.getParameter(ORDER_BY);
            if (isNullOrEmpty(orderBy)) {
                orderBy = "product_name ASC";
            }
            String filter = request.getParameter(FILTER);
            if( isNullOrEmpty(filter)) {
                filter = "'%'";
            } else {
                filter = "'%" + filter + "%'";
            }

            int currentPage = Integer.parseInt(currentPageString);
            int pageLimit = Integer.parseInt(currentPageLimit);
            final Page<Product> pagedRequest = new Page<>();
            pagedRequest.setPageNumber(currentPage);
            pagedRequest.setLimit(pageLimit);
            pagedRequest.setOrderBy(orderBy);
            pagedRequest.setFilter(filter);
            Page<Product> pagedProduct = productService.findPageService(pagedRequest);

            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute(PRODUCTS_PAGES_ATTRIBUTE, pagedProduct);

            if (request.getParameter(MESSAGE) == null) {
                request.getSession().setAttribute(MESSAGE, "");
            }
            servletContext.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServiceException | NullPointerException e) {
            throw new ControllerException(e.getMessage());
        }
    }
}
