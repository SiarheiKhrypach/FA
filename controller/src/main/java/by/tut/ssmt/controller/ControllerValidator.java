package by.tut.ssmt.controller;

import by.tut.ssmt.service.exception.NullOrEmptyException;

import static java.util.Objects.isNull;

public class ControllerValidator {

    public void isNotNullOrEmpty(String s) throws NullOrEmptyException {
        if (isNull(s) || "".equals(s.trim())) {
            throw new   NullOrEmptyException("The incoming string is null or empty");
        }
    }
}
