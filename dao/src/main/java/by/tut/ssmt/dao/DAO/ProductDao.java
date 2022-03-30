package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.Product;

import java.util.ArrayList;

public interface ProductDao {

    ArrayList<Product> select() throws DaoException;

    Product selectOne(int productId);

    void insert(Product product);

    void update(Product product);

    void delete(String productName) throws DaoException;
//    void delete(int productId);

}
