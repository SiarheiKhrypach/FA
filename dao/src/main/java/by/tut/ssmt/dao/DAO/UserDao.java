package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.User;

import java.util.ArrayList;

public interface UserDao {

    ArrayList<User> select() throws DaoException;

    User find (User user) throws DaoException;

    User selectOne(int userId);

    void insert(User user) throws DaoException;

    void update(User user);

    void delete(String userName);
//    public void delete(int userId);
}
