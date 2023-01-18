package by.tut.ssmt.dao.DAO;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;

import java.util.List;

public interface UserDao {

    /**
     * selects all users
     *
     * @return List<User>
     * @throws DaoException is a module exception
     */
    List<User> selectDao() throws DaoException;

    /**
     * finds user
     *
     * @param user - user credentials, received from UI
     * @return User
     * @throws DaoException is a module exception
     */
    User find(User user) throws DaoException;

    /**
     * @param userId
     * @return
     */
    User selectOneDao(int userId) throws DaoException;

    /**
     * selects page of users
     * @param usersPaged - Page request frame
     * @return Page<String>
     * @throws DaoException
     */
    Page<String> findPageDao(Page<User> usersPaged) throws DaoException;

    /**
     * inserts users
     *
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean insert(User user) throws DaoException;

    /**
     * @param user
     * @return
     */
    boolean update(User user) throws DaoException;

    /**
     * deletes users
     * @param userName - user credentials, received from UI
     * @throws DaoException - is a module exceptions
     */
    void delete(String userName) throws DaoException;
}
