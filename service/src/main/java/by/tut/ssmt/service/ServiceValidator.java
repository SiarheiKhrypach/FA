package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.domain.User;
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

public void isNotZero(final int i) {
        if(i == 0) {
            throw new NullPointerException("The incoming int is 0");
        }
}

    public void isNotNull(final List list) {
        if (isNull(list)) {
            throw new NullPointerException("The incoming list is null");
        }
    }

    public void isNotNull(final MenuItem menuItem) {
        if (isNull(menuItem)) {
            throw new NullPointerException("The incoming menuitem is null");
        }
    }

    public void isNotNull(final Page page) {
        if (isNull(page)) {
            throw new NullPointerException("The incoming page is null");
        }
    }

    public void isNotNull(final Product product) {
        if (isNull(product)) {
            throw new NullPointerException("The incoming product is null");
        }
    }

    public void isNotNull(final User user) {
        if (isNull(user)) {
            throw new NullPointerException("The incoming user is null");
        }
    }


    public void isNotNullOrEmpty(String...names) throws NullOrEmptyException {
        for (String name: names) {
            if (isNull(name) || (name.trim()).isEmpty()) {
//        if (isNull(s) || "".equals(s.trim())) {
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
