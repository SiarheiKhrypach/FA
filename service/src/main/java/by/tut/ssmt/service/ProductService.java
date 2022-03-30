package by.tut.ssmt.service;

import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.exceptions.ServiceException;

import java.util.List;

public interface ProductService {

    List<Product> selectAllService() throws ServiceException;

    Product selectOneService(int productId);

    void addService(Product product);

    void updateService(Product product);

    void deleteService(String productName) throws ServiceException;
//    void deleteService(int productId);


}
