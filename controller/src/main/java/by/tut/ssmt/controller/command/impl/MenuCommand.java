package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.ControllerValidator;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.dataProcessor.DataProcessorList;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.tut.ssmt.controller.util.ControllerConstants.*;
import static by.tut.ssmt.controller.util.Util.isNullOrEmpty;


public class MenuCommand extends FormsAccessCommand {

    List<Product> products;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final MenuService menuService = serviceFactory.getMenuService();
    private final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            String currentPageString = request.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageString)) {
                currentPageString = "1";
            }
            request.getSession().setAttribute(CURRENT_PAGE, currentPageString);
            String currentPageLimit = request.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentPageLimit)) {
                currentPageLimit = "5";
            }
            String currentUser = (String) request.getSession().getAttribute(USER_NAME);
            controllerValidator.isNotNullOrEmpty(currentUser);
            int currentPage = Integer.parseInt(currentPageString);
            int pageLimit = Integer.parseInt(currentPageLimit);
            final Page<Product> pagedRequest = new Page<>();
            pagedRequest.setPageNumber(currentPage);
            pagedRequest.setLimit(pageLimit);
            pagedRequest.setCurrentUser(currentUser);
            Page<Product> pagedMenuItem = menuService.findPageService(pagedRequest);
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute(MENU_ITEMS_PAGED_ATTRIBUTE, pagedMenuItem);
            products = menuService.selectAllFromMenuService(currentUser);
            setProportion(request);
            if (request.getParameter(MESSAGE) == null) {
                request.getSession().setAttribute(MESSAGE, "You are in the menu now");
            }
            super.execute(request, response);
        } catch (ServiceException | NullOrEmptyException | NullPointerException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    private void setProportion(HttpServletRequest request) throws NullPointerException {
        final String formattedProportion = dataProcessorList.calculate(products);
        serviceValidator.isNotNull(formattedProportion);
        request.getSession().setAttribute(PROPORTION, formattedProportion);
    }
}
