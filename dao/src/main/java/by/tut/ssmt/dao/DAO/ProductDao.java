package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;

import java.util.List;

public interface ProductDao {

    /**
     * selects all products
     * @return List<Product>
     * @throws DaoException
     */
    List<Product> selectAllProductDao() throws DaoException;


    /**
     * selects page of products
     * @param productPaged - Page request frame
     * @return Page<Product>
     * @throws DaoException
     */
    Page<Product> findProductPageDao(Page<Product> productPaged) throws DaoException;


    /**
         * selects one product by id provided
         * @param productId - provided id, received from UI
         * @return Product corresponding to given id
         * @throws DaoException is a module exception
         */
    Product selectOneProductDao(int productId) throws DaoException;

    /**
     * adds product
     * @param product - product to be added, received from UI
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean insertProductDao(Product product) throws DaoException;

    /**
     * changes product
     * @param product is a new product instead of old one
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean updateProductDao(Product product) throws DaoException;

    /**
     * deletes product
     * @param productName - name of the product to be deleted
     * @throws DaoException - is a module exception
     */
    boolean deleteProductDao(String productName) throws DaoException;

}
