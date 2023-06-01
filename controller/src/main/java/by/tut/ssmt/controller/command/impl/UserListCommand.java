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

import static by.tut.ssmt.controller.util.ControllerConstants.*;
import static by.tut.ssmt.controller.util.Util.isNullOrEmpty;

public class UserListCommand extends FormsAccessCommand{

    List<User> users;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private final ServiceValidator serviceValidator = serviceFactory.getServiceValidator();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        try {
            String currentPageString = request.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageString)) {
                currentPageString = "1";
            }
            request.getSession().setAttribute(CURRENT_PAGE, currentPageString);
            String currentPageLimit = request.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentPageLimit)) {
                currentPageLimit = "5";
            }
            String orderBy = request.getParameter(ORDER_BY);
            if (isNullOrEmpty(orderBy)) {
                orderBy = "users.user_name ASC";
            }
            String filter = request.getParameter(FILTER);
            if (isNullOrEmpty(filter)) {
                filter = "'%'";
            } else {
                filter = "'%" + filter + "%'";
            }

            int currentPage = Integer.parseInt(currentPageString);
            int pageLimit = Integer.parseInt(currentPageLimit);
            final Page<User> pagedRequest = new Page<>();
            pagedRequest.setPageNumber(currentPage);
            pagedRequest.setLimit(pageLimit);
            pagedRequest.setOrderBy(orderBy);
            pagedRequest.setFilter(filter);

            Page<String> pagedUserList = userService.findUserPageService(pagedRequest);
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute(USERS_PAGED_ATTRIBUTE, pagedUserList);
            if (request.getParameter(MESSAGE) == null) {
                request.getSession().setAttribute(MESSAGE, "You are in the user list now");
            }
            super.execute(request, response);
        } catch (ServiceException | NullPointerException e) {
            throw new ControllerException(e.getMessage());
        }

    }
}
