package by.tut.ssmt.command.impl;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.UserDao;
import by.tut.ssmt.command.Command;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.formDataCollectors.UserFormDataCollector;

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
    final UserDao userDao = new UserDao(dbConnector);
    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        users = userDao.select();
        validator.isNotNull(users);
        passwordVerified = false;
        User user;
        try {
            user = dataCollector.collectFormData(request);
            verify(user);
            request.setAttribute("name", user.getUserName());
            postToMainPage(request, response);
        } catch (NullOrEmptyException | ServletException | IOException e) {
            request.setAttribute("message", "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
