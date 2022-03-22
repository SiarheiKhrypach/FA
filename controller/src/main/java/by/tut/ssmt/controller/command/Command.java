package by.tut.ssmt.controller.command;

import by.tut.ssmt.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
void execute (final HttpServletRequest request, final HttpServletResponse response) throws ControllerException, ServletException, IOException;
}
