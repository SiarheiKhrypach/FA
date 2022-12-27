package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteFromMenuCommand implements Command  {
//public class DeleteFromMenuCommand extends FormsAccessCommand implements Command  {

    private boolean menuItemDeleted;

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final MenuService menuService = serviceFactory.getMenuService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            final String productName = request.getParameter("productName");
            menuService.deleteService(productName);
            String currentPageString = (String) request.getSession().getAttribute("currentPage");
            response.sendRedirect("/menu?command=menu&currentPage=" + currentPageString );
        } catch (ServiceException e) {
            throw new ControllerException();
        }
    }
}
