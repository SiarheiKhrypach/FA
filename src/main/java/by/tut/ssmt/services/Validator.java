package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;

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


    public void validate(String s) throws NullOrEmptyException {
        if (isNull(s) || "".equals(s)) {
            throw new NullOrEmptyException("The incoming string is null");
        }
    }


}
