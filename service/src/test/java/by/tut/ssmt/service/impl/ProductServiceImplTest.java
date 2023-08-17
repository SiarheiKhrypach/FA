package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.ServiceException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceImplTest {

    private final ProductService productService = ServiceFactory.getInstance().getProductService();

    @Test
    public void testSelectAllProductService_positive() throws ServiceException {
        final List<Product> expectedProducts = Arrays.asList(
                new Product(1179, "lopp", 34, 2, 0)
        );
        final int expectedLength = expectedProducts.size();

        List<Product> actualProducts = productService.selectAllProductService();

        assertEquals(expectedLength, actualProducts.size());
        assertEquals(expectedProducts, actualProducts);

    }

    @Test
    public void testFindProductPageService_positive() throws ServiceException {
        final Page<Product> productPageRequest = new Page<>();
        productPageRequest.setLimit(5);
        productPageRequest.setPageNumber(1);
        productPageRequest.setFilter("'%'");
        productPageRequest.setOrderBy("product_name ASC");

        Page<Product> expectedPagedProductList = new Page(1, 1, 5, null, null, null, Arrays.asList(new Product(1179, "lopp", 34, 2, 0)));
        Page<Product> actualPagedProductList = productService.findProductPageService(productPageRequest);

        assertEquals(expectedPagedProductList, actualPagedProductList);
    }

    @Test(expected = ServiceException.class)
    public void testFindProductPageService_negative() throws ServiceException{
        final Page<Product> productPageRequest = null;
        productService.findProductPageService(productPageRequest);
    }

    @Test
    public void testSelectOneService_positive() throws ServiceException {
        int productId = 1179;

        Product expectedProduct = new Product(1179, "lopp", 34, 2, 0);
        Product actualProduct = productService.selectOneService(productId);
        assertEquals(expectedProduct, actualProduct);

    }

    @Test (expected = ServiceException.class)
    public void testSelectOneService_negative() throws ServiceException {
        int productId = 0;
        productService.selectOneService(productId);
    }

    @Test
    public void testAddProductService_positive() throws ServiceException {
        Product testProduct = new Product("testProductName", 23,23, 0);
        boolean actualAddProductServiceResult = productService.addProductService(testProduct);
        assertEquals(true, actualAddProductServiceResult);
        productService.deleteProductService(testProduct.getProductName());
    }

    @Test(expected = ServiceException.class)
    public void testAddProductService_negative() throws ServiceException {
        Product testProduct = null;
        productService.addProductService(testProduct);
    }

    @Test
    public void testUpdateService_positive() throws ServiceException {
        Product testProductChanged = new Product(1179, "lopp", 1, 1, 0);
        boolean actualUpdateProductServiceResult = productService.updateService(testProductChanged);
        assertEquals(true, actualUpdateProductServiceResult);
        Product originalProduct = new Product(1179, "lopp", 34, 2, 0);
        productService.updateService(originalProduct);
    }

    @Test (expected = ServiceException.class)
    public void testUpdateService_negative() throws ServiceException {
        Product testProductChanged = null;
        productService.updateService(testProductChanged);
    }

    @Test
    public void testDeleteProductService_positive() throws ServiceException {
        Product testProduct = new Product("testProduct", 34, 32, 0);
        productService.addProductService(testProduct);
        boolean actualDeleteProductResult = productService.deleteProductService(testProduct.getProductName());
        assertEquals(true, actualDeleteProductResult);
    }

    @Test (expected = ServiceException.class)
    public void testDeleteProductService_negative() throws ServiceException {
        Product testProduct = new Product(null, 0, 0);
        productService.deleteProductService(testProduct.getProductName());
    }
}