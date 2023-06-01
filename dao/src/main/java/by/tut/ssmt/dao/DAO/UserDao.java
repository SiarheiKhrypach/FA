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
    List<User> selectUserDao() throws DaoException;

    /**
     * finds user
     *
     * @param user - user credentials, received from UI
     * @return User
     * @throws DaoException is a module exception
     */
    User findUserDao(User user) throws DaoException;

    /**
     * @param userId
     * @return
     */
    User selectOneUserDao(int userId) throws DaoException;

    /**
     * selects page of users
     * @param usersPaged - Page request frame
     * @return Object
     * @throws DaoException
     */
    Page<?> findUserPageDao(Page<User> usersPaged) throws DaoException;

    /**
     * inserts users
     *
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws DaoException is a module exception
     */
    boolean insertUserDao(User user) throws DaoException;

    /**
     * @param user
     * @return boolean
     */
    boolean updateUserDao(User user) throws DaoException;

    /**
     * deletes users
     * @param userName - user credentials, received from UI
     * @throws DaoException - is a module exceptions
     * @return boolean
     */
    boolean deleteUserDao(String userName) throws DaoException;
}
