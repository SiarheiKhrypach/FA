package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.ArrayList;

public interface ProductService {

    ArrayList<Product> selectAllService() throws ServiceException;

    Product selectOneService(int productId) throws ServiceException;

    boolean addService(Product product) throws ServiceException;

    boolean updateService(Product product) throws ServiceException;

    void deleteService(String productName) throws ServiceException;
//    void deleteService(int productId);


}
