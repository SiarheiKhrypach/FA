package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.UserDB;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.formDataCollectors.UserFormDataCollector;

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
    boolean loginAndPassAreNotTaken = true;
    private ArrayList<User> users;
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
    final UserFormDataCollector dataCollector = new UserFormDataCollector();


    public void init() {
        LOGGER.info("Call to init - loginAndPassAreNotTaken - " + loginAndPassAreNotTaken);
        users = UserDB.select();
        validator.isValidData(users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = dataCollector.collectFormData(req);
            verify (user);
            if (loginAndPassAreNotTaken) {UserDB.insert(user);}
            postToMainPage(req, resp);
        } catch (NullOrEmptyException e) {
            req.setAttribute("message", "Please fill out the form");
            req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
        }
    }

    private void verify(User user) {
        loginAndPassAreNotTaken = true;
        for (int i = 0; i < users.size(); i++) {
            if ((user.getUserName().equals(users.get(i).getUserName())) || (user.getPassword().equals(users.get(i).getPassword()))) {
                loginAndPassAreNotTaken = false;
            }
        }
    }

    private void postToMainPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (loginAndPassAreNotTaken) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "User name or/and password are already in use, try one more time");
            req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
        }
    }
}