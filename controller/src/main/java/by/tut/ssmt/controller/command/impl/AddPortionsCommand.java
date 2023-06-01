package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.ControllerFactory;
import by.tut.ssmt.controller.command.AbstractCommand;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.formDataCollector.FormDataCollector;
import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tut.ssmt.controller.util.ControllerConstants.*;



public class AddPortionsCommand extends AbstractCommand implements Command {

    private final ControllerFactory controllerFactory = ControllerFactory.getInstance();
    private final FormDataCollector dataCollector = controllerFactory.getMenuFormDataCollector();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final MenuService menuService = serviceFactory.getMenuService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {

        try {
            final MenuItem menuItem = (MenuItem) dataCollector.collectFormData(request);
            boolean result = menuService.addMenuService(menuItem);
            checkOperationForSuccess(request, result);
            response.sendRedirect("/main?" + MESSAGE + "=" + result);
        } catch (NullOrEmptyException e) {
            request.setAttribute(MESSAGE, "Please enter valid data");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }
}
