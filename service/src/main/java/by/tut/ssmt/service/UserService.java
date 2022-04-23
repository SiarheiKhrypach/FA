package by.tut.ssmt.service;

import by.tut.ssmt.service.exception.ServiceException;
import by.tut.ssmt.dao.domain.User;

import java.util.ArrayList;

public interface UserService {

    /**
     * Service to select all users
     * @return ArrayList<User>
     * @throws ServiceException is a module exception
     */
    ArrayList<User> selectAllService() throws ServiceException;

    /**
     * login service
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean loginService(User user) throws ServiceException;

    User selectOneDaoService(int userId) throws ServiceException;

    /**
     * Service to add users
     * @param user - user credentials, received from UI
     * @return true if successful, otherwise false
     * @throws ServiceException is a module exception
     */
    boolean insertService(User user) throws ServiceException;

    boolean updateService(User user) throws ServiceException;

    void deleteService(String userName) throws ServiceException;
//    public void deleteService(int userId);
}
