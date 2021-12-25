package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.UserDao;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.formDataCollectors.UserFormDataCollector;

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
    boolean passwordVerified;
    private ArrayList<User> users;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final UserDao userDao = new UserDao(dbConnector);
    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    public void init() throws ServletException {
        users = userDao.select();
        validator.isNotNull(users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        passwordVerified = false;
        User user;
        try {
            user = dataCollector.collectFormData(req);
            verify(user);
            req.setAttribute("name", user.getUserName());
            postToMainPage(req, resp);
        } catch (NullOrEmptyException e) {
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
        LOGGER.info("Call to verify()" + users);
        for (int i = 0; i < users.size(); i++) {
            if ((user.equals(users.get(i)))) {
                passwordVerified = true;
            }
        }
    }
}
