package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.ProductDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = DaoFactory.getInstance().getProductDao();

    public ProductServiceImpl() {
    }

    @Override
    public List<Product> selectAllService() throws ServiceException {
        try {
            return productDao.selectDao();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Product> findPageService(Page<Product> productPagedRequest) throws ServiceException {
        try {
            return productDao.findPageDao(productPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Product selectOneService(int productId) throws ServiceException {
        try {
            return productDao.selectOneDao(productId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addService(Product product) throws ServiceException {
        try {
            return productDao.insertDao(product);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateService(Product product) throws ServiceException {
        try {
            return productDao.updateDao(product);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteService(String productName) throws ServiceException {
////    public void deleteService(int productId) {
//        productDao.delete(productName);
////        productDao.delete(productId);
        try {
            productDao.deleteDao(productName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }
}
