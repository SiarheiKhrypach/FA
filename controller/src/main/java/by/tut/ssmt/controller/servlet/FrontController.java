package by.tut.ssmt.controller.servlet;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.command.impl.*;
import by.tut.ssmt.service.exceptions.ControllerException;
import org.apache.log4j.spi.RootLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@WebServlet
        (
                name = "FrontController",
                urlPatterns = {"", "/", "/update", "/register", "/login", "/main"},
                initParams = {@WebInitParam(name = "command", value = "default")}
        )
public class FrontController extends HttpServlet {


    private Map<String, Command> commands;


    @Override
    public void init() throws ServletException {
        initCommandsMap();
    }

    private void initCommandsMap() {
        if (isNull(commands)) {
            commands = new HashMap<>();
        }
        commands.put("add", new AddCommand());
        commands.put("default", new DefaultCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("edit", new EditCommand());
        commands.put("editform", new EditFormCommand());
        commands.put("form", new FormsAccessCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doExecute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isRequiredForward = true;
        RootLogger log = (RootLogger) getServletContext().getAttribute("log4");
        isRequiredForward = processLocale(request, response);
        if (isRequiredForward) {
            try {
                final String command = getCommand(request);
                commands.get(command).execute(request, response);
            } catch (ControllerException | ServletException | IOException e) {
                log.error("Error: ", e);
            }
        }
    }

    private boolean processLocale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isRequiredForward = true;
        String command = request.getParameter("command");
        if ("locale".equals(command)) {
            request.setAttribute("command", "default");
            String locale = request.getParameter("locale");
            request.getSession().setAttribute("locale", locale);
            isRequiredForward = false;
            response.sendRedirect("/");
        }
        return isRequiredForward;
    }

    private String getCommand(HttpServletRequest request) {
        String commandNameParam = request.getParameter("command");
        if (isNull(commandNameParam)) {
            commandNameParam = "default";
        }
        return commandNameParam;
    }
}
