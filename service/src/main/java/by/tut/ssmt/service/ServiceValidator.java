package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import java.util.List;

import static java.util.Objects.isNull;

public class ServiceValidator {

    public void isNotNull(final String str) {
        if (isNull(str)) {
            throw new NullPointerException("The incoming string is null");
//            throw new ControllerException ("The incoming string is null");
        }
    }

    public void isNotNull(final List list) {
        if (isNull(list)) {
            throw new NullPointerException("The incoming list is null");
        }
    }

    public void isNotNull(final Product product) {
        if (isNull(product)) {
            throw new NullPointerException("The incoming product is null");
        }
    }


    public void isNotNullOrEmpty(String s) throws NullOrEmptyException {
        if (isNull(s) || "".equals(s.trim())) {
            throw new NullOrEmptyException("The incoming string is null or empty");
        }
    }

    public void isNotNullOrEmpty(char[] ch) throws NullOrEmptyException {
        if (isNull(ch) || ch.length == 0) {
//        if (isNull(ch) || "".equals(ch[0])) {
            throw new NullOrEmptyException("The incoming string is null or empty");
        }
    }
}
