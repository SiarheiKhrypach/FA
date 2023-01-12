package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.dataProcessor.DataProcessorList;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class DeleteFromMenuCommand implements Command {

    List<Product> products;

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final MenuService menuService = serviceFactory.getMenuService();
    private final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            final String productName = request.getParameter("productName");
            String currentUser = (String) request.getSession().getAttribute("userName");
            menuService.deleteService(productName, currentUser);
            products  = menuService.selectAllFromMenuService(currentUser);
            setProportion(request);
            String currentPageString = (String) request.getSession().getAttribute("currentPage");
            response.sendRedirect("/menu?command=menu&currentPage=" + currentPageString);
        } catch (ServiceException e) {
            throw new ControllerException();
        }
    }

    private void setProportion(HttpServletRequest request) throws NullPointerException {
        final String formattedProportion = dataProcessorList.calculate(products);
        serviceValidator.isNotNull(formattedProportion);
        request.getSession().setAttribute("proportion", formattedProportion);
    }

}

