package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;

public interface MenuDao {


    /**
     * selects page of menu items
     *
     * @param menuItemPaged - Page request frame
     * @return Page<Product>
     */
    Page<Product> findPageDao(Page<Product> menuItemPaged) throws DaoException;

    /**
     * adds product
     *
     * @param menuItem - product to be added to the menu, received from UI
//     * @param menuItem - product to be added to the menu, received from UI
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean insertDao(MenuItem menuItem) throws DaoException;


    /**
     * deletes product
     *
     * @param menuItemName - product to be deleted from the menu, received from UI
     * @throws DaoException - is a module Exception
     */
    void deleteDao(String menuItemName) throws DaoException;

    /**
     * change portion number of the product
     *
     * @param menuItem - product with portion number changed, received from UI
     * @throws DaoException - is a module Exception
     */
    void portionChangeDao(MenuItem menuItem) throws DaoException;
}
