package by.tut.ssmt.controller.command;

import javax.servlet.http.HttpServletRequest;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class AbstractCommand {

    protected void checkOperationForSuccess (HttpServletRequest request, boolean result) {
        if (result) {
            request.getSession().setAttribute(MESSAGE, "Successful operation");
        } else {
            request.getSession().setAttribute(MESSAGE, "Operation failed");
        }
    }
}
