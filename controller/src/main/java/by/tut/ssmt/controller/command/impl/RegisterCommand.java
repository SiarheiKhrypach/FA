package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.UserDaoImpl;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import by.tut.ssmt.controller.services.formDataCollectors.UserFormDataCollector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterCommand implements Command {

    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final UserDaoImpl userDaoImpl = new UserDaoImpl(dbConnector);
    boolean loginAndPassAreNotTaken = true;
    private ArrayList<User> users;
    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        users = userDaoImpl.select();
        validator.isNotNull(users);
        try {
            User user = dataCollector.collectFormData(request);
            verify (user);
            if (loginAndPassAreNotTaken) {
                userDaoImpl.insert(user);}
            postToMainPage(request, response);
        } catch (NullOrEmptyException | ServletException | IOException e) {
            request.setAttribute("message", "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    private void verify(User user) {
        loginAndPassAreNotTaken = true;
        for (int i = 0; i < users.size(); i++) {
            // checked equality of both names and passwords
            if ((user.getUserName().equals(users.get(i).getUserName())) || (user.getPassword().equals(users.get(i).getPassword()))) {
//            if (user.equals(users.get(i))) {
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

