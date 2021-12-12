package by.tut.ssmt.services.formDataCollectors;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public abstract class FormDataCollector {
    final Validator validator = new Validator();

    public abstract Object collectFormData (HttpServletRequest req) throws NullOrEmptyException;
}
