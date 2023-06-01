package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public interface MenuService {


    /**
     * Service to show all menu items for current user
     * @return List<Product>
     * @throws ServiceException is module exception
     */
    List<Product> selectAllFromMenuService(String currentUser) throws ServiceException;

    /**
     * Service to show page of products
     *
     * @param menuItemPagedRequest - paged form of request
     * @return Page<Product>
     */
    Page<Product> findMenuPageService(Page<Product> menuItemPagedRequest) throws ServiceException;

    /**
     * Service to add product to the menu
     *
     * @param menuItem - product to be added, received from UI
     *                 //     * @param menuItem - product to be added, received from UI
     * @throws ServiceException is a module exception
     */
    boolean addMenuService(MenuItem menuItem) throws ServiceException;

    /**
     * Service to delete product from the menu
     *
     * @param productName - product to delete from menu of
     * @param userName - user
     * @return boolean
     * @throws ServiceException is a module exception
     */
    boolean deleteMenuService(String productName, String userName) throws ServiceException;

    /**
     * Service to chane portions number of menu item in a bulk
     *
     * @param menuList - list of products with updated portion number, received from UI
     * @throws ServiceException is a module exception
     */
    boolean bulkPortionChangeMenuService(List<MenuItem> menuList) throws ServiceException;
}