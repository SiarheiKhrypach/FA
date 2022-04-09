package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.domain.Product;

import java.util.ArrayList;

public interface ProductDao {

    /**
     * selects all products
     * @return ArrayList<Product>
     * @throws DaoException
     */
    ArrayList<Product> selectDao() throws DaoException;

    /**
     * selects one product by id provided
     * @param productId - provided id, received from UI
     * @return Product corresponding to given id
     * @throws DaoException is a module exception
     */
    Product selectOneDao(int productId) throws DaoException;

    /**
     * adds product
     * @param product - product to be added, received from UI
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean insertDao(Product product) throws DaoException;

    /**
     * changes product
     * @param product is a new product instead of old one
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean updateDao(Product product) throws DaoException;

    /**
     * deletes product
     * @param productName - name of the product to be deleted
     * @throws DaoException - is a module exception
     */
    void deleteDao(String productName) throws DaoException;
//    void delete(int productId);

}
