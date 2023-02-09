package by.tut.ssmt.service;

import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import static java.util.Objects.isNull;

public class ServiceValidator {

public void isNotZero(final int i) throws ServiceException {
        if(i == 0) {
            throw new ServiceException("The incoming int is 0");
        }
}

    public <T> void isNotNull(final T t) throws NullPointerException {
        if (isNull(t)) {
            throw new NullPointerException("The incoming data is null");
        }
    }

    public void isNotNullOrEmpty(String...names) throws NullOrEmptyException {
        for (String name: names) {
            if (isNull(name) || (name.trim()).isEmpty()) {
                throw new NullOrEmptyException("The incoming string is null or empty");
            }
        }
    }

    public void isNotNullOrEmpty(char[] ch) throws NullOrEmptyException {
        if (isNull(ch) || ch.length == 0) {
            throw new NullOrEmptyException("The incoming string is null or empty");
        }
    }
}
