package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.UserDB;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    String message;
    boolean passwordVerified;
    private ArrayList<User> users;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    final Validator validator = new Validator();

    public void init() throws ServletException {
        users = UserDB.select();
        validator.isValidData(users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        passwordVerified = false;

        User user = null;
        try {
            user = collectData(req);
            verify(user);
            req.setAttribute("name", user.getUserName());
            postToMainPage(req, resp);
        } catch (NullOrEmptyException e) {
            message = "Please fill out the form";
            req.setAttribute("message", message);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }

    private void postToMainPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (passwordVerified == true) {
            message = "Welcome, ";
            HttpSession session = req.getSession();
            session.setAttribute("message", message);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            message = "User name or/and password are not valid";
            req.setAttribute("message", message);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private User collectData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = req.getParameter("name");
        validator.validate(userName);
        final String password = req.getParameter("pass");
        validator.validate(password);
        User user = new User(userName, password);
        return user;
    }

    private void verify(User user) {
        LOGGER.info("Call to verify()" + users);
        for (int i = 0; i < users.size(); i++) {
            if ((user.equals(users.get(i)))) {
                passwordVerified = true;
            }
        }
    }
}
