package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class UserFormDataCollector extends FormDataCollector {

    @Override
    public User collectFormData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = req.getParameter("name");
        controllerValidator.isNotNullOrEmpty(userName);
        final String password = req.getParameter("pass");
//        char[] password = req.getParameter("pass").trim().toCharArray();
        controllerValidator.isNotNullOrEmpty(password);
        final User user = new User(userName, password);
        return user;
    }
}
