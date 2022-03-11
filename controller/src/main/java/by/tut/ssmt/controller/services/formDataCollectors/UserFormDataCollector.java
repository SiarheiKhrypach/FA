package by.tut.ssmt.controller.services.formDataCollectors;

import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class UserFormDataCollector extends FormDataCollector {

    @Override
    public User collectFormData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = req.getParameter("name");
        validator.isNotNullOrEmpty(userName);
        final String password = req.getParameter("pass");
//        char[] password = req.getParameter("pass").trim().toCharArray();
        validator.isNotNullOrEmpty(password);
        User user = new User(userName, password);
        return user;
    }
}
