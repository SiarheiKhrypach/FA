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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    final Validator validator = new Validator();
    String message;
    private ArrayList<User> users;
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    public void init() throws ServletException {
        users = UserDB.select();
        validator.isValidData(users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            collectDataForDB(req);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (NullOrEmptyException e) {
            message = "Please fill out the form";
            req.setAttribute("message", message);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

    }

    private void collectDataForDB(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = req.getParameter("name");
        validator.validate(userName);
        final String password = req.getParameter("pass");
        validator.validate(password);
        final User user = new User(userName, password);
        UserDB.insert(user);
    }
}