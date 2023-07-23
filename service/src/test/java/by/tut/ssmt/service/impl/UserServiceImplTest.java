package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.ServiceException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceImplTest {


    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final ServiceValidator serviceValidator = new ServiceValidator();


    @Test
    public void testSelectAllUserService() throws DaoException, ServiceException {
        final List<User> expectedUsers = Arrays.asList(
                new User(1, "admin", "root"),
                new User(5, "zxvb", "xcbxcb")
        );
        final int expectedLength = expectedUsers.size();

        List<User> actualUsers = userService.selectAllUserService();

        assertEquals(expectedLength, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testFindUserPageService() throws DaoException, ServiceException {
        final Page<User> userPagedRequest = new Page<>();
        userPagedRequest.setPageNumber(1);
        userPagedRequest.setLimit(5);
        userPagedRequest.setOrderBy("users.user_name ASC");
        userPagedRequest.setFilter("'%'");


        Page<String> expectedPagedUserList = new Page(1, 2, 5, null, null, null, Arrays.asList("admin", "zxvb"));
        Page<String> actualPagedUserList = (Page<String>) userService.findUserPageService(userPagedRequest);

        assertEquals(expectedPagedUserList, actualPagedUserList);
    }

    @Test
    public void testLoginService() throws DaoException, ServiceException {

        User testUser = new User ("admin", "root");
        boolean expectedLoginServiceResult = true;
//        User expectedLoginServiceResult = new User("admin", "root");
        boolean actualLoginServiceResult = userService.loginService(testUser);
//        User actualLoginServiceResult = userService.loginService(expectedLoginServiceResult);
//        User actualLoginServiceResult = userDao.findUserDao(expectedLoginServiceResult);

        assertEquals(expectedLoginServiceResult, actualLoginServiceResult);
    }

    @Test
    public void testSelectOneUserService() throws DaoException, ServiceException {
        int userId = 1;

        User expectedUser = new User (1, "admin", "root");
        User actualUser = userService.selectOneUserService(userId);
//        User actualUser = userDao.selectOneUserDao(userId);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testRegisterUserService() throws DaoException, ServiceException {

        User testUser = new User("testUserName", "testUserPassword");

        boolean actualRegisterServiceResult = userService.registerUserService(testUser);
//        boolean actualLoginServiceResult = userDao.insertUserDao(testUser);

        assertEquals(true, actualRegisterServiceResult);
//        userService.deleteUserService(testUser);

    }

    @Test
    public void testUpdateUserService() throws ServiceException {

        User testUserChanged = new User("testUserName", "testUserPasswordChanged");
        boolean actualUpdateUserServiceResult = userService.updateUserService(testUserChanged);
        assertEquals(true, actualUpdateUserServiceResult);
    }

    @Test
    public void deleteUserService() throws ServiceException {
        String userName = "testUserName";

        boolean actualDeleteUserServiceResult = userService.deleteUserService(userName);
        assertEquals(true, actualDeleteUserServiceResult);

    }
}