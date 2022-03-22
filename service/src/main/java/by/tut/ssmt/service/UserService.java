package by.tut.ssmt.service;

import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.exceptions.ServiceException;

import java.util.ArrayList;

public interface UserService {

    public ArrayList<User> selectService() throws ServiceException;

    public User selectOneService(int userId);

    public void insertService(User user);

    public void updateService(User user);

    public void deleteService(String userName);
//    public void deleteService(int userId);
}
