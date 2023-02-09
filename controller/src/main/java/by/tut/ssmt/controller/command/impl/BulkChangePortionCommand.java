package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.ControllerFactory;
import by.tut.ssmt.controller.command.AbstractCommand;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.formDataCollector.FormDataCollector;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.dataProcessor.DataProcessorList;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.tut.ssmt.controller.util.ControllerConstants.*;



public class BulkChangePortionCommand extends AbstractCommand implements Command {

    List<Product> products;

    private final ControllerFactory controllerFactory = ControllerFactory.getInstance();
    private final FormDataCollector dataCollector = controllerFactory.getBulkMenuFormDataCollector();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();
    private final MenuService menuService = serviceFactory.getMenuService();
    private final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            final List menuList = (List) dataCollector.collectFormData(request);
            boolean result = menuService.bulkPortionChangeService(menuList);
            checkOperationForSuccess(request, result);
            String currentUser = (String) request.getSession().getAttribute(USER_NAME);
            products  = menuService.selectAllFromMenuService(currentUser);
            setProportion(request);
            String currentPageString = (String) request.getSession().getAttribute(CURRENT_PAGE);
            response.sendRedirect("/menu?command=menu&" + CURRENT_PAGE + "=" + currentPageString + "&" + MESSAGE + "=" + result);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        } catch (NullOrEmptyException e) {
            request.setAttribute(MESSAGE, "Please enter valid data" );
            request.getRequestDispatcher("/WEB-INF/menu.jsp");
        }
    }

    private void setProportion(HttpServletRequest request) throws ServiceException {
        final String formattedProportion = dataProcessorList.calculate(products);
        serviceValidator.isNotNull(formattedProportion);
        request.getSession().setAttribute(PROPORTION, formattedProportion);
    }


}
