package by.tut.ssmt.services.formDataCollectors;

import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class UserFormDataCollector extends FormDataCollector {

    @Override
    public User collectFormData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = req.getParameter("name");
        validator.validate(userName);
        final String password = req.getParameter("pass");
        validator.validate(password);
        User user = new User(userName, password);
        return user;
    }
}
