package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class MenuFormDataCollector extends FormDataCollector {

    @Override
    public MenuItem collectFormData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = (String) req.getSession().getAttribute(USER_NAME);
        controllerValidator.isNotNullOrEmpty(userName);
        final String productId = req.getParameter(PRODUCT_ID);
        controllerValidator.isNotNullOrEmpty(productId);
        final String portions = req.getParameter(PORTIONS);
        controllerValidator.isNotNullOrEmpty(portions);
        MenuItem menuItem = new MenuItem (userName, Integer.parseInt(productId), Integer.parseInt(portions));
        return menuItem;
    }
}
