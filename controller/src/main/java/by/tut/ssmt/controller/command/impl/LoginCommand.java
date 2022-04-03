package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.services.formDataCollectors.UserFormDataCollector;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import by.tut.ssmt.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    boolean passwordVerified;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    UserService userService = serviceFactory.getUserService();
    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        passwordVerified = false;
        try {
            User user = dataCollector.collectFormData(request);
            passwordVerified = userService.loginService(user);
            request.setAttribute("name", user.getUserName());
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (passwordVerified) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Welcome, ");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "User name or/and password are not valid");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
