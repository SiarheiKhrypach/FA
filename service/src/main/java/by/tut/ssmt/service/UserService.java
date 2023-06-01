package by.tut.ssmt.service;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    /**
     * Service to select all users
     * @return List<User>
     * @throws ServiceException is a module exception
     */
    List<User> selectAllUserService() throws ServiceException;

    /**
     * Service to show page of users
     * @param userPagedRequest - paged form of request
     * @return Page<String>
     * @throws ServiceException
     */
    Page<String> findUserPageService(Page<User> userPagedRequest) throws ServiceException;

    /**
     * login service
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean loginService(User user) throws ServiceException;

    User selectOneUserService(int userId) throws ServiceException;

    /**
     * Service to add users
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean registerUserService(User user) throws ServiceException;

    boolean updateUserService(User user) throws ServiceException;


    /**
     * Service to delete users
     * @param userName - user credential, received from Ui
     * @throws ServiceException is a module exception
     */
    boolean deleteUserService(String userName) throws ServiceException;
}
