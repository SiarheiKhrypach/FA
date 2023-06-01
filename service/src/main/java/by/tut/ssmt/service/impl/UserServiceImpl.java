package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final ServiceValidator serviceValidator = new ServiceValidator();

    public UserServiceImpl() {
    }

    @Override
    public List<User> selectAllUserService() throws ServiceException {
        try {
            return userDao.selectUserDao();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Page<String> findUserPageService(Page<User> userPagedRequest) throws ServiceException {
        try {
            serviceValidator.isNotNull(userPagedRequest);
            return (Page<String>) userDao.findUserPageDao(userPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean loginService(User user) throws ServiceException {
        try {
            serviceValidator.isNotNull(user);
            user = userDao.findUserDao(user);
            return user != null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User selectOneUserService(int userId) throws ServiceException {
        try {
            serviceValidator.isNotZero(userId);
            return userDao.selectOneUserDao(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean registerUserService(User user) throws ServiceException {
        try {
            serviceValidator.isNotNull(user);
            return userDao.insertUserDao(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updateUserService(User user) throws ServiceException {
        try {
            serviceValidator.isNotNull(user);
            return userDao.updateUserDao(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean deleteUserService(String userName) throws ServiceException {
        try {
            serviceValidator.isNotNull(userName);
            return userDao.deleteUserDao(userName);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
