package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.controller.ControllerValidator;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public abstract class FormDataCollector {

 protected final ControllerValidator controllerValidator = new ControllerValidator();

    public abstract Object collectFormData (HttpServletRequest req) throws NullOrEmptyException;
}
