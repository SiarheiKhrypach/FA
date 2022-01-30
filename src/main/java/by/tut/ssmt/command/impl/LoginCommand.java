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
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        users = userDao.select();
        validator.isNotNull(users);
        passwordVerified = false;
        User user;
        try {
            user = dataCollector.collectFormData(req);
            verify(user);
            req.setAttribute("name", user.getUserName());
            postToMainPage(req, resp);
        } catch (NullOrEmptyException | ServletException | IOException e) {
            req.setAttribute("message", "Please fill out the form");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

    private void postToMainPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (passwordVerified) {
            HttpSession session = req.getSession();
            session.setAttribute("message", "Welcome, ");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "User name or/and password are not valid");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
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
