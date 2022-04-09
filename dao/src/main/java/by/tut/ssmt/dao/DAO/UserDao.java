package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.domain.User;

import java.util.ArrayList;

public interface UserDao {

    /**
     * selects all users
     * @return ArrayList<User>
     * @throws DaoException is a module exception
     */
    ArrayList<User> selectDao() throws DaoException;

    /**
     * finds user
     * @param user - user credentials, received from UI
     * @return User
     * @throws DaoException is a module exception
     */
    User find (User user) throws DaoException;

    /**
     *
     * @param userId
     * @return
     */
    User selectOneDao(int userId);

    /**
     * inserts users
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean insert(User user) throws DaoException;

    /**
     *
     * @param user
     */
    void update(User user);

    /**
     *
     * @param userName
     */
    void delete(String userName);
//    public void delete(int userId);
}
