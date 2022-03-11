package by.tut.ssmt.service;

import by.tut.ssmt.dao.repository.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> selectAllService();

    Product selectOneService(int productId);

    void addService(Product product);

    void updateService(Product product);

    void deleteService(String productName);
//    void deleteService(int productId);


}
