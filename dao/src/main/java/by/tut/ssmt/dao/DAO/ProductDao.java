package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.domain.Product;

import java.util.ArrayList;

public interface ProductDao {

    ArrayList<Product> selectDao() throws DaoException;

    Product selectOneDao(int productId) throws DaoException;

    boolean insertDao(Product product) throws DaoException;

    boolean updateDao(Product product) throws DaoException;

    void deleteDao(String productName) throws DaoException;
//    void delete(int productId);

}
