package by.tut.ssmt.controller.servlet;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.command.impl.*;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.util.CommandEnum;
import org.apache.log4j.spi.RootLogger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static by.tut.ssmt.controller.util.CommandEnum.DEFAULT;
import static by.tut.ssmt.controller.util.ControllerConstants.*;
import static java.util.Objects.isNull;

@WebServlet
        (
                name = "FrontController",
//                urlPatterns = {"/update", "/register", "/login", "/main", "/add"},
                loadOnStartup = 0
        )
public class FrontController extends HttpServlet {

    private Map <CommandEnum, Command> commands;
//    private Map<String, Command> commands;

    @Override
    public void init() throws ServletException {

        initCommandsMap();
        RootLogger log = (RootLogger) getServletContext().getAttribute(LOG4);

        ServletContext servletContext = getServletContext();
        servletContext.setAttribute(MESSAGE, "default");

    }

    private void initCommandsMap() {
        if (isNull(commands)) {
            commands = new EnumMap<>(CommandEnum.class);
//            commands = new HashMap<>();
        }
        commands.put(CommandEnum.ADD, new AddCommand());
        commands.put(CommandEnum.ADD_PORTIONS, new AddPortionsCommand());
        commands.put(CommandEnum.BULK_CHANGE_PORTION, new BulkChangePortionCommand());
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(CommandEnum.DELETE, new DeleteCommand());
        commands.put(CommandEnum.DELETE_FROM_MENU, new DeleteFromMenuCommand());
        commands.put(CommandEnum.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandEnum.EDIT, new EditCommand());
        commands.put(CommandEnum.EDIT_FORM, new EditFormCommand());
        commands.put(CommandEnum.FORM, new FormsAccessCommand());
        commands.put(CommandEnum.LOGIN, new LoginCommand());
        commands.put(CommandEnum.LOGOUT, new LogoutCommand());
        commands.put(CommandEnum.MENU, new MenuCommand());
        commands.put(CommandEnum.REGISTER, new RegisterCommand());
        commands.put(CommandEnum.USER_LIST, new UserListCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doExecute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean isRequiredForward;
        RootLogger log = (RootLogger) getServletContext().getAttribute(LOG4);
        isRequiredForward = processLocale(request, response);
        if (isRequiredForward) {
            try {
                final CommandEnum command = getCommand(request);
                commands.get(command).execute(request, response);
            } catch (ServletException | IOException | ControllerException e) {
                log.error(ControllerException.getCause(e));
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }
        }
    }

    private boolean processLocale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isRequiredForward = true;
        String command = request.getParameter(COMMAND);
        if ("locale".equals(command)) {
            request.setAttribute(COMMAND, DEFAULT);
            String locale = request.getParameter(LOCALE);
            request.getSession().setAttribute(LOCALE, locale);
            isRequiredForward = false;
            response.sendRedirect("/");
        }
        return isRequiredForward;
    }

    private CommandEnum getCommand(HttpServletRequest request) {
        String commandNameParam = request.getParameter(COMMAND);
        if (isNull(commandNameParam)) {
            commandNameParam = DEFAULT.getName();
        }
        return CommandEnum.valueOf(commandNameParam.toUpperCase());
    }
}
