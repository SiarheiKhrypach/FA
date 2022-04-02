package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.Product;

import java.util.ArrayList;

public interface ProductDao {

    ArrayList<Product> select() throws DaoException;

    Product selectOne(int productId) throws DaoException;

    void insert(Product product) throws DaoException;

    void update(Product product) throws DaoException;

    void delete(String productName) throws DaoException;
//    void delete(int productId);

}
