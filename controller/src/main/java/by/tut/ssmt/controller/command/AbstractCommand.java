package by.tut.ssmt.controller.command;

import javax.servlet.http.HttpServletRequest;

public class AbstractCommand {

    protected void checkOperationForSuccess (HttpServletRequest request, boolean result) {
        if (result) {
            request.getSession().setAttribute("message", "Successful operation");
        } else {
            request.getSession().setAttribute("message", "Operation failed");
        }
    }
}
