package by.tut.ssmt.service;

import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Validator {

    public void isNotNull(final String str) {
        if (isNull(str)) {
            throw new NullPointerException("The incoming string is null");
        }
    }

    public void isNotNull(final ArrayList list) {
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
            throw new   NullOrEmptyException("The incoming string is null or empty");
        }
    }

    public void isNotNullOrEmpty(char[] ch) throws NullOrEmptyException {
        if (isNull(ch) || ch.length == 0) {
//        if (isNull(ch) || "".equals(ch[0])) {
            throw new NullOrEmptyException("The incoming string is null or empty");
        }
    }
}
