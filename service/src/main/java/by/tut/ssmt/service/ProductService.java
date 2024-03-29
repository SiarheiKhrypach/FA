package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public interface ProductService {


    /**
     * Service to show all products
     * @return List<Product>
     * @throws ServiceException is module exception
     */
    List<Product> selectAllProductService() throws ServiceException;

    /**
     * Service to show page of products
     * @param productPagedRequest - paged form of request
     * @return Page<Product>
     * @throws ServiceException
     */
    Page<Product> findProductPageService(Page<Product> productPagedRequest) throws ServiceException;

    /**
     * Service to select one product by id provided
     * @param productId - provided id, received from UI
     * @return Product
     * @throws ServiceException
     */
    Product selectOneService(int productId) throws ServiceException;

    /**
     * Servide to add a product
     * @param product - product to be added, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean addProductService(Product product) throws ServiceException;

    /**
     * Service to update product
     * @param product - product to be updated, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean updateService(Product product) throws ServiceException;

    /**
     * Service to delete product
     * @param productName - name of the product to be deleted, received form UI
     * @throws ServiceException is a module exception
     */
    boolean deleteProductService(String productName) throws ServiceException;

}
