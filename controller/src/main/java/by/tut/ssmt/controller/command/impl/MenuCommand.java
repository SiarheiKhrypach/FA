package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tut.ssmt.controller.util.Util.isNullOrEmpty;


public class MenuCommand extends FormsAccessCommand {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final MenuService menuService = serviceFactory.getMenuService();


    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            String currentPageString = request.getParameter("currentPage");
            if (isNullOrEmpty(currentPageString)) {
                currentPageString = "1";
            }
            request.getSession().setAttribute("currentPage", currentPageString);
            String currentPageLimit = request.getParameter("pageLimit");
            if (isNullOrEmpty(currentPageLimit)) {
                currentPageLimit = "5";
            }
            String currentUser = (String) request.getSession().getAttribute("userName");
            int currentPage = Integer.parseInt(currentPageString);
            int pageLimit = Integer.parseInt(currentPageLimit);
            final Page<Product> pagedRequest = new Page<>();
            pagedRequest.setPageNumber(currentPage);
            pagedRequest.setLimit(pageLimit);
            pagedRequest.setCurrentUser(currentUser);
            Page<Product> pagedMenuItem = menuService.findPageService(pagedRequest);
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute("menuItemsPagedAttribute", pagedMenuItem);
            super.execute(request, response);
        } catch (ServiceException | NullPointerException e) {
            throw new ControllerException(e);
        }
    }
}
