package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.domain.User;

import java.util.ArrayList;

public interface UserDao {

    ArrayList<User> selectDao() throws DaoException;

    User find (User user) throws DaoException;

    User selectOneDao(int userId);

    boolean insert(User user) throws DaoException;

    void update(User user);

    void delete(String userName);
//    public void delete(int userId);
}
