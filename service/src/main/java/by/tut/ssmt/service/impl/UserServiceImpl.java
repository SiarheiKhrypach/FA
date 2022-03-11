package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.UserService;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getUserDao();

    public UserServiceImpl() {
    }

    @Override
    public ArrayList<User> selectService() {
        return userDao.select();
    }

    @Override
    public User selectOneService(int userId) {
        return userDao.selectOne(userId);
    }

    @Override
    public void insertService(User user) {
        userDao.insert(user);
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
