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
    public List<User> selectAllService() throws ServiceException {
        try {
            return userDao.selectDao();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Page<String> findPageService(Page<User> userPagedRequest) throws ServiceException {
        try {
            serviceValidator.isNotNull(userPagedRequest);
            return userDao.findPageDao(userPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean loginService(User user) throws ServiceException {
        try {
            serviceValidator.isNotNull(user);
            user = userDao.find(user);
            return user != null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User selectOneDaoService(int userId) throws ServiceException {
        try {
            serviceValidator.isNotZero(userId);
            return userDao.selectOneDao(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean registerService(User user) throws ServiceException {
        try {
            serviceValidator.isNotNull(user);
            return userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updateService(User user) throws ServiceException {
        try {
            serviceValidator.isNotNull(user);
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean deleteService(String userName) throws ServiceException {
        try {
            serviceValidator.isNotNull(userName);
            return userDao.delete(userName);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
