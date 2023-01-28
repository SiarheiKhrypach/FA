package by.tut.ssmt.controller.formDataCollector;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.NullOrEmptyException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class BulkMenuFormDataCollector extends FormDataCollector {

    List <MenuItem> menuItems = new ArrayList<>();

    @Override
    public List collectFormData(HttpServletRequest req) throws NullOrEmptyException {
        final String userName = (String) req.getSession().getAttribute(USER_NAME);
        controllerValidator.isNotNullOrEmpty(userName);
        Page<Product> pagedMenuItem = (Page<Product>) req.getServletContext().getAttribute(MENU_ITEMS_PAGED_ATTRIBUTE);
        List <Product> elements = pagedMenuItem.getElements();
        for(Product product:elements)
            if(req.getParameter(product.getProductName())!=null) {
                menuItems.add(new MenuItem (userName, product.getProductId(), Integer.parseInt(req.getParameter(product.getProductName()))));
        }
        return menuItems;
    }
}
