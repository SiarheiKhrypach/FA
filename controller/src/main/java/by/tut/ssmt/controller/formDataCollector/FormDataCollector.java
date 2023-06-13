package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.controller.ControllerValidator;
import by.tut.ssmt.controller.exception.NotEqualOperatorsException;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

import static by.tut.ssmt.controller.util.ControllerConstants.PASSWORD;
import static by.tut.ssmt.controller.util.ControllerConstants.RE_PASSWORD;

public abstract class FormDataCollector {

 protected final ControllerValidator controllerValidator = new ControllerValidator();

    public abstract Object collectFormData (HttpServletRequest req) throws NullOrEmptyException;

    public void rePass(HttpServletRequest request) throws NotEqualOperatorsException, NullOrEmptyException {
        final String password = request.getParameter(PASSWORD);
        controllerValidator.isNotNullOrEmpty(password);
        final String rePassword = request.getParameter(RE_PASSWORD);
        controllerValidator.areEqual(password, rePassword);
    }
}
