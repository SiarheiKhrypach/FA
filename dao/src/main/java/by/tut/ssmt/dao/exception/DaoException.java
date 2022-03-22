package by.tut.ssmt.dao.exception;

public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }


}
