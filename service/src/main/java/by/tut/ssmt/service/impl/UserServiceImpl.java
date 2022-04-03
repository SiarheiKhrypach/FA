package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exceptions.ServiceException;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getUserDao();

    public UserServiceImpl() {
    }

    @Override
    public ArrayList<User> selectAllService() throws ServiceException {
        try {
            return userDao.select();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean loginService(User user) throws ServiceException {
        try {
            user = userDao.find(user);
            return user != null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User selectOneService(int userId) {
        return userDao.selectOne(userId);
    }

    @Override
    public boolean insertService(User user) throws ServiceException {
        try {
            return userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateService(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteService(String userName) {
//    public void deleteService(int userId) {
//        userDao.delete(userId);
        userDao.delete(userName);
    }
}
