package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.exceptions.NegativeNumberException;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.exceptions.ZeroException;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Validator {

    public void isValidData(final String str) {
        if (isNull(str)) {
            throw new NullPointerException("The incoming string is null");
        }
    }

    public void isValidData(final ArrayList list) {
        if (isNull(list)) {
            throw new NullPointerException("The incoming list is null");
        }
    }

    public void isValidData(final Product product) {
        if (isNull(product)) {
            throw new NullPointerException("The incoming product is null");
        }
    }


    public void isNotNullOrEmpty(String s) throws NullOrEmptyException {
        if (isNull(s) || "".equals(s)) {
            throw new NullOrEmptyException("The incoming string is null");
        }
    }


    public void isNotNullOrNegativeNumber(String s) throws NullOrEmptyException, NegativeNumberException {
        if (isNull(s) || "".equals(s)) {
            throw new NullOrEmptyException("The incoming string is null");
        }
        if (Double.parseDouble(s) < 0) {
            throw new NegativeNumberException("The incoming data represents negative number");
        }
    }


    public void isNotNullZeroOrNegativeNumber(String s) throws NullOrEmptyException, NegativeNumberException, ZeroException {
        if (isNull(s) || "".equals(s)) {
            throw new NullOrEmptyException("The incoming string is null");
        }
        if (Double.parseDouble(s) < 0) {
            throw new NegativeNumberException("The incoming data represents zero or negative number");
        }
        if (Double.parseDouble(s) == 0) {
            throw new ZeroException("The incoming data represents zero");
        }
    }


}
