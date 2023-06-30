package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.ServiceValidator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest {


    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final ServiceValidator serviceValidator = new ServiceValidator();


    @Test
    public void testSelectAllUserService() throws DaoException {
        final List<User> expectedUsers = Arrays.asList(
                new User(1, "admin", "root"),
                new User(5, "zxvb", "xcbxcb")
        );
        final int expectedLength = expectedUsers.size();

        List<User> actualUsers = userDao.selectUserDao();

        assertEquals(expectedLength, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testFindUserPageService() throws DaoException {
        final Page<User> userPagedRequest = new Page<>();
        userPagedRequest.setPageNumber(1);
        userPagedRequest.setLimit(5);
        userPagedRequest.setOrderBy("users.user_name ASC");
        userPagedRequest.setFilter("'%'");


        Page<String> expectedPagedUserList = new Page(1, 2, 5, null, null, null, Arrays.asList("admin", "zxvb"));
        Page<String> actualPagedUserList = (Page<String>) userDao.findUserPageDao(userPagedRequest);

        assertEquals(expectedPagedUserList, actualPagedUserList);
    }

    @Test
    public void testLoginService() throws DaoException {

        User expectedLoginServiceResult = new User("admin", "root");
        User actualLoginServiceResult = userDao.findUserDao(expectedLoginServiceResult);

        assertEquals(expectedLoginServiceResult, actualLoginServiceResult);
    }

    @Test
    public void selectOneUserService() {
    }

    @Test
    public void registerUserService() {
    }

    @Test
    public void updateUserService() {
    }

    @Test
    public void deleteUserService() {
    }
}