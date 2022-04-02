package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.services.formDataCollectors.UserFormDataCollector;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import by.tut.ssmt.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterCommand implements Command {


    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    final Validator validator = new Validator();
    boolean loginAndPassAreNotTaken = true;
    private ArrayList<User> users;
    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            users = userService.selectAllService();
            validator.isNotNull(users);
            User user = dataCollector.collectFormData(request);
            verify(user);
            if (loginAndPassAreNotTaken) {
                userService.insertService(user);
            }
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void verify(User user) {
        loginAndPassAreNotTaken = true;
        for (int i = 0; i < users.size(); i++) {
            // checked equality of both names and passwords
            if ((user.getUserName().equals(users.get(i).getUserName())) || (user.getPassword().equals(users.get(i).getPassword()))) {
                loginAndPassAreNotTaken = false;
            }
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (loginAndPassAreNotTaken) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "User name or/and password are already in use, try one more time");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }
}

