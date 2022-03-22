package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.UserDaoImpl;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.services.formDataCollectors.UserFormDataCollector;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class LoginCommand implements Command {
    boolean passwordVerified;
    private ArrayList<User> users;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final UserDaoImpl userDaoImpl = new UserDaoImpl(dbConnector);

    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        passwordVerified = false;
        try {
//        users = userService.selectService();
            users = userDaoImpl.select();
            validator.isNotNull(users);
            User user = dataCollector.collectFormData(request);
            verify(user);
            request.setAttribute("name", user.getUserName());
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } catch (ServletException | IOException | DaoException e) {
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

    private void verify(User user) {
        for (int i = 0; i < users.size(); i++) {
            if ((user.equals(users.get(i)))) {
                passwordVerified = true;
            }
        }
    }
}
