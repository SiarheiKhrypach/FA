package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.ProductDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = DaoFactory.getInstance().getProductDao();
    private final ServiceValidator serviceValidator = new ServiceValidator();

    public ProductServiceImpl() {
    }

    @Override
    public List<Product> selectAllProductService() throws ServiceException {
        try {
            return productDao.selectAllProductDao();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Page<Product> findProductPageService(Page<Product> productPagedRequest) throws ServiceException {
        try {
            serviceValidator.isNotNull(productPagedRequest);
            return productDao.findProductPageDao(productPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public Product selectOneService(int productId) throws ServiceException {
        try {
            serviceValidator.isNotZero(productId);
            return productDao.selectOneProductDao(productId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean addProductService(Product product) throws ServiceException {
        try {
            serviceValidator.isNotNull(product);
            return productDao.insertProductDao(product);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updateService(Product product) throws ServiceException {
        try {
            serviceValidator.isNotNull(product);
            return productDao.updateProductDao(product);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean deleteProductService(String productName) throws ServiceException {
        try {
            serviceValidator.isNotNull(productName);
            return productDao.deleteProductDao(productName);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
