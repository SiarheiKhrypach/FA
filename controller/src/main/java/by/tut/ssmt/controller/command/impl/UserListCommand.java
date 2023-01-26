package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.tut.ssmt.controller.util.Util.isNullOrEmpty;

public class UserListCommand extends FormsAccessCommand{

    List<User> users;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            String currentPageString = request.getParameter("currentPage");
            if (isNullOrEmpty(currentPageString)) {
                currentPageString = "1";
            }
            request.getSession().setAttribute("currentPage", currentPageString);
            String currentPageLimit = request.getParameter("pageLimit");
            if (isNullOrEmpty(currentPageLimit)) {
                currentPageLimit = "5";
            }
            int currentPage = Integer.parseInt(currentPageString);
            int pageLimit = Integer.parseInt(currentPageLimit);
            final Page<User> pagedRequest = new Page<>();
            pagedRequest.setPageNumber(currentPage);
            pagedRequest.setLimit(pageLimit);
            Page<String> pagedUserList = userService.findPageService(pagedRequest);
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute("usersPagedAttribute", pagedUserList);
            if (request.getParameter("message") == null) {
                request.getSession().setAttribute("message", "You are in the user list now");
            }
            super.execute(request, response);
        } catch (ServiceException | NullPointerException e) {
            throw new ControllerException(e);
        }

    }
}
