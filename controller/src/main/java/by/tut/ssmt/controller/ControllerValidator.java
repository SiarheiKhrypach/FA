package by.tut.ssmt.controller;

import by.tut.ssmt.controller.exception.NotEqualOperatorsException;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import static java.util.Objects.isNull;

public class ControllerValidator {

    public void isNotNullOrEmpty(String s) throws NullOrEmptyException {
        if (isNull(s) || (s.trim()).isEmpty()) {
//        if (isNull(s) || "".equals(s.trim())) {
            throw new   NullOrEmptyException("The incoming string is null or empty");
        }
    }

    public void areEqual(String a, String b) throws NotEqualOperatorsException {
        if (!a.equals(b)) {
            throw new NotEqualOperatorsException("The passwords do not match");
        }
    }

}
