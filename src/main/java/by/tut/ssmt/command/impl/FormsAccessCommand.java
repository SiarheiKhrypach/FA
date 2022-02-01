package by.tut.ssmt.command.impl;

import by.tut.ssmt.command.Command;
import by.tut.ssmt.services.exceptions.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormsAccessCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, ServletException, IOException {
        String uri = req.getRequestURI().replace("/", "");
        req.getRequestDispatcher("/WEB-INF/" + uri + ".jsp").forward(req, resp);
    }
}

