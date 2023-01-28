package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.ControllerFactory;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.formDataCollector.FormDataCollector;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.tut.ssmt.controller.util.ControllerConstants.*;


public class LoginCommand implements Command {
    boolean passwordVerified;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private final ControllerFactory controllerFactory = ControllerFactory.getInstance();
    private final FormDataCollector dataCollector = controllerFactory.getUserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        passwordVerified = false;
        try {
            final User user = (User) dataCollector.collectFormData(request);
            passwordVerified = userService.loginService(user);
            request.setAttribute(NAME, user.getUserName());
//            request.setAttribute("userId", user.getUserId());
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute(MESSAGE, "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (passwordVerified && request.getAttribute(NAME).equals(ADMIN)) {
            HttpSession session = request.getSession();
            session.setAttribute(MESSAGE, WELCOME);
            session.setAttribute(ROLE, ADMIN);
            session.setAttribute(USER_NAME, request.getAttribute(NAME));
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (passwordVerified && !request.getAttribute(NAME).equals(ADMIN)) {
            HttpSession session = request.getSession();
            session.setAttribute(MESSAGE, WELCOME);
            session.setAttribute(ROLE, USER);
//            session.setAttribute("name", request.getAttribute("name"));
            session.setAttribute(USER_NAME, request.getAttribute(NAME));
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute(MESSAGE, "User name or/and password are not valid");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
