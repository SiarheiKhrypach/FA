package by.tut.ssmt.controller.exception;

import static java.util.Objects.nonNull;

public class NotEqualOperatorsException extends Exception {

    public static Throwable getCause(Throwable cause) {
        if (nonNull(cause.getCause())) {
            cause = getCause(cause.getCause());
        }
        return cause;
    }

    public NotEqualOperatorsException() {
        super();
    }

    public NotEqualOperatorsException(Exception e) {
        super(e);
    }

    public NotEqualOperatorsException(String message) {
        super(message);
    }

    public NotEqualOperatorsException(String message, Exception e) {
        super(message, e);
    }


}