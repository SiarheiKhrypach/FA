package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormsAccessCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        if (request.getParameter("message") == null) {
            request.getSession().setAttribute("message", "You are in the edit form now");
        }
        final String uri = request.getRequestURI().replace("/", "");
        request.getRequestDispatcher("/WEB-INF/" + uri + ".jsp").forward(request, response);
    }
}

