package by.tut.ssmt.services.exceptions;

public class ControllerException extends Exception {
    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String message) {
        super(message);
    }
}
