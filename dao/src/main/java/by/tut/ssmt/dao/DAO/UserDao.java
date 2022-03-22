package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.User;

import java.util.ArrayList;

public interface UserDao {

    public ArrayList<User> select() throws DaoException;

    public User selectOne(int userId);

    public void insert(User user);

    public void update(User user);

    public void delete(String userName);
//    public void delete(int userId);
}
