package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.EntryValidatorImpl;
import by.tut.ssmt.services.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    Validator validator = new EntryValidatorImpl();
    String message;
    String welcomeMessage;
    boolean passwordVerified;
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
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        passwordVerified = false;
        if (validator.validate(req.getParameter("name")) &&
                validator.validate(req.getParameter("pass"))) {
            User user = collectData(req);
            verify (user);
            req.setAttribute("name", user.getUserName());
            postToMainPage(req, resp);
        } else {
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

    private User collectData (HttpServletRequest req) {
        final String userName = req.getParameter("name");
        final String password = req.getParameter("pass");
        User user = new User(userName, password);
        return user;
    }

    private void verify (User user) {
//        for (int i = 0; i<users.size(); i++) {
        LOGGER.info("Call to verify()" + users);
        for (int i = 0; i < users.size(); i++) {
            if ((user.equals(users.get(i)))) {
            passwordVerified = true;
            }
//        if (user.equals(userEntry.get(i))) {
//            passwordVerified = true;
//        }
        }
    }
}
