package by.tut.ssmt.controller.services.formDataCollectors;

import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public abstract class FormDataCollector {
    final Validator validator = new Validator();

    public abstract Object collectFormData (HttpServletRequest req) throws NullOrEmptyException;
}
