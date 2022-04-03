package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.ProductDao;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.exceptions.ServiceException;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = DaoFactory.getInstance().getProductDao();

    public ProductServiceImpl() {
    }

    @Override
    public List<Product> selectAllService() throws ServiceException {
        try {
            return productDao.select();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Product selectOneService(int productId) throws ServiceException {
        try {
            return productDao.selectOne(productId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addService(Product product) throws ServiceException {
        try {
            return productDao.insert(product);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateService(Product product) throws ServiceException {
        try {
            return productDao.update(product);
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
            productDao.delete(productName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }
}
