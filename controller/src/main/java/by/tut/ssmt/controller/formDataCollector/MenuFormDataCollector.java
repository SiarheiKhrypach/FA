package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

public class MenuFormDataCollector extends FormDataCollector {

    @Override
    public MenuItem collectFormData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = (String) req.getSession().getAttribute("userName");
        controllerValidator.isNotNullOrEmpty(userName);
        final String productId = req.getParameter("productId");
        controllerValidator.isNotNullOrEmpty(productId);
        final String portions = req.getParameter("portions");
        controllerValidator.isNotNullOrEmpty(portions);
        MenuItem menuItem = new MenuItem (userName, Integer.parseInt(productId), Integer.parseInt(portions));
        return menuItem;
    }
}
