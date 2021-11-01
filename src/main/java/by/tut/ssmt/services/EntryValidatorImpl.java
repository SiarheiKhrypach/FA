package by.tut.ssmt.services;

public class EntryValidatorImpl implements Validator {

    public boolean validate(String s) {
        return s != null;
    }
}
