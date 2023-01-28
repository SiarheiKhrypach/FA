package by.tut.ssmt.controller.servlet;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.command.impl.*;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.UserService;
import org.apache.log4j.spi.RootLogger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.tut.ssmt.controller.util.ControllerConstants.*;
import static java.util.Objects.isNull;

@WebServlet
        (
                name = "FrontController",
//                urlPatterns = {"/update", "/register", "/login", "/main", "/add"},
                loadOnStartup = 0
        )
public class FrontController extends HttpServlet {

    private List<Product> products;
    private List<User> users;
    private Map<String, Command> commands;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();
    private final ProductService productService = serviceFactory.getProductService();
    private final UserService userService = serviceFactory.getUserService();


    @Override
    public void init() throws ServletException {

        initCommandsMap();
        RootLogger log = (RootLogger) getServletContext().getAttribute(LOG4);

        ServletContext servletContext = getServletContext();
        servletContext.setAttribute(MESSAGE, "default");

    }

    private void initCommandsMap() {
        if (isNull(commands)) {
            commands = new HashMap<>();
        }
        commands.put("add", new AddCommand());
        commands.put("addPortions", new AddPortionsCommand());
        commands.put("bulkChangePortion", new BulkChangePortionCommand());
        commands.put("default", new DefaultCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("deleteFromMenu", new DeleteFromMenuCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("edit", new EditCommand());
        commands.put("editForm", new EditFormCommand());
        commands.put("form", new FormsAccessCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("menu", new MenuCommand());
        commands.put("register", new RegisterCommand());
        commands.put("userList", new UserListCommand());
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
                final String command = getCommand(request);
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
            request.setAttribute(COMMAND, "default");
            String locale = request.getParameter(LOCALE);
            request.getSession().setAttribute(LOCALE, locale);
            isRequiredForward = false;
            response.sendRedirect("/");
        }
        return isRequiredForward;
    }

    private String getCommand(HttpServletRequest request) {
        String commandNameParam = request.getParameter(COMMAND);
        if (isNull(commandNameParam)) {
            commandNameParam = "default";
        }
        return commandNameParam;
    }
}
