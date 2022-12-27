package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.ServiceException;

public interface MenuService {

    /**
     * Service to show page of products
     *
     * @param menuItemPagedRequest - paged form of request
     * @return Page<Product>
     */
    Page<Product> findPageService(Page<Product> menuItemPagedRequest) throws ServiceException;

    /**
     * Service to add product to the menu
     *
     * @param menuItem - product to be added, received from UI
//     * @param menuItem - product to be added, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean addService(MenuItem menuItem) throws ServiceException;

    /**
     * Service to delete product from the menu
     *
     * @param productName - product to delete, received from UI
     * @throws ServiceException is a module exception
     */
    void deleteService(String productName) throws ServiceException;


    /**
     * Service to change portions number of menu item
     *
     * @param menuItem - product with portion number changed, received from UI
     * @throws ServiceException is a module exception
     */
    void portionChangeService(MenuItem menuItem) throws ServiceException;
}
