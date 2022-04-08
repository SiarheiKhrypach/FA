package by.tut.ssmt.service;

import by.tut.ssmt.service.exception.ServiceException;
import by.tut.ssmt.dao.domain.User;

import java.util.ArrayList;

public interface UserService {

    public ArrayList<User> selectAllService() throws ServiceException;

    boolean loginService(User user) throws ServiceException;

    public User selectOneDaoService(int userId);

    public boolean insertService(User user) throws ServiceException;

    public void updateService(User user);

    public void deleteService(String userName);
//    public void deleteService(int userId);
}
