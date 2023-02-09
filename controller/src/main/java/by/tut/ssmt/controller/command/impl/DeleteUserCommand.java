package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.ControllerValidator;
import by.tut.ssmt.controller.command.AbstractCommand;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class DeleteUserCommand extends AbstractCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            final String userName = request.getParameter(USER_NAME);
            controllerValidator.isNotNullOrEmpty(userName);
            boolean result = userService.deleteService(userName);
            checkOperationForSuccess(request, result);
            String currentPageString = (String) request.getSession().getAttribute(CURRENT_PAGE);
            response.sendRedirect("/userList?command=userList&" + CURRENT_PAGE + "=" + currentPageString + "&" + MESSAGE + "=" + result);
        } catch (ServiceException | NullOrEmptyException e) {
            throw new ControllerException(e.getMessage());
        }
    }
}
