package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.AbstractCommand;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserCommand extends AbstractCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            final String userName = request.getParameter("userName");
            boolean result = userService.deleteService(userName);
            checkOperationForSuccess(request, result);
            String currentPageString = (String) request.getSession().getAttribute("currentPage");
            response.sendRedirect("/userList?command=userList&currentPage=" + currentPageString + "&message=" + result);
        } catch (ServiceException e) {
            throw new ControllerException();
        }
    }
}
