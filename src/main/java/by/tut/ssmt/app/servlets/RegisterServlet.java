package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.EntryValidatorImpl;
import by.tut.ssmt.services.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebServlet ("/register")
public class RegisterServlet extends HttpServlet {
    final Validator validator = new EntryValidatorImpl();
    private final ArrayList userList = new ArrayList();
    String message;
    private ArrayList<User> users;
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    public void init() throws ServletException {
        final Object users = getServletContext().getAttribute("usersInContext");
        if (users == null) {
            throw new IllegalStateException("Initialization error in LoginServlet!");
        } else {
            this.users = (ArrayList<User>) users;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (validator.validate(req.getParameter("name")) &&
            validator.validate(req.getParameter("pass"))) {
            collectdata(req);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            message = "Please fill out the form";
            req.setAttribute("message", message);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

    }

    private void collectdata(HttpServletRequest req) {
        final String userName = req.getParameter("userName");
        final String password = req.getParameter("pass");
        final User user = new User (userName, password);
        users.add(user);

    }
}
