package by.tut.ssmt.dao.exception;

import static java.util.Objects.nonNull;

public class DaoException extends Exception {

    public static Throwable getCause(Throwable cause) {
        if (nonNull(cause.getCause())) {
            cause = getCause(cause.getCause());
        }
        return cause;
    }

    public DaoException() {
        super();
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }


}
