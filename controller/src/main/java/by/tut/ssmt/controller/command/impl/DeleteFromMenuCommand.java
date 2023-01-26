package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.AbstractCommand;
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


public class DeleteFromMenuCommand extends AbstractCommand implements Command {

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
            boolean result = menuService.deleteService(productName, currentUser);
            checkOperationForSuccess(request, result);
            setProportion(request, currentUser);
            String currentPageString = (String) request.getSession().getAttribute("currentPage");
            response.sendRedirect("/menu?command=menu&currentPage=" + currentPageString + "&message=" + result);
        } catch (ServiceException e) {
            throw new ControllerException();
        }
    }

    private void setProportion(HttpServletRequest request, String currentUser) throws NullPointerException, ServiceException {
        products = menuService.selectAllFromMenuService(currentUser);
        final String formattedProportion = dataProcessorList.calculate(products);
        serviceValidator.isNotNull(formattedProportion);
        request.getSession().setAttribute("proportion", formattedProportion);
    }

}

