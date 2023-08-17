package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.ServiceException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceImplTest {


    private final UserService userService = ServiceFactory.getInstance().getUserService();


    @Test
    public void testSelectAllUserService_positive() throws ServiceException {
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
    public void testFindUserPageService_positive() throws ServiceException {
        final Page<User> userPagedRequest = new Page<>();
        userPagedRequest.setPageNumber(1);
        userPagedRequest.setLimit(5);
        userPagedRequest.setOrderBy("users.user_name ASC");
        userPagedRequest.setFilter("'%'");

        Page<String> expectedPagedUserList = new Page(1, 2, 5, null, null, null, Arrays.asList("admin", "zxvb"));
        Page<String> actualPagedUserList = userService.findUserPageService(userPagedRequest);

        assertEquals(expectedPagedUserList, actualPagedUserList);
    }

    @Test(expected = ServiceException.class)
    public void testFindUserPageService_negative() throws ServiceException {
        final Page<User> userPagedRequest = null;
        userService.findUserPageService(userPagedRequest);
    }

    @Test
    public void testLoginService_positive() throws ServiceException {

        User testUser = new User("admin", "root");
        boolean expectedLoginServiceResult = true;
        boolean actualLoginServiceResult = userService.loginService(testUser);
        assertEquals(expectedLoginServiceResult, actualLoginServiceResult);
    }

    @Test(expected = ServiceException.class)
    public void testLoginService_negative() throws ServiceException {
        User testUser = null;
        userService.loginService(testUser);
    }

    @Test
    public void testSelectOneUserService_positive() throws ServiceException {
        int userId = 1;

        User expectedUser = new User(1, "admin", "root");
        User actualUser = userService.selectOneUserService(userId);

        assertEquals(expectedUser, actualUser);
    }

    @Test(expected = ServiceException.class)
    public void testSelectOneUserService_negative() throws ServiceException {
        int userId = 0;
        userService.selectOneUserService(userId);
    }

    @Test
    public void testRegisterUserService_positive() throws ServiceException {

        User testUser = new User("testUserName", "testUserPassword");
        boolean actualRegisterServiceResult = userService.registerUserService(testUser);
        assertEquals(true, actualRegisterServiceResult);
        userService.deleteUserService(testUser.getUserName());

    }

    @Test(expected = ServiceException.class)
    public void testRegisterUserService_negative() throws ServiceException {
        User testUser = null;
        userService.registerUserService(testUser);

    }

    @Test
    public void testUpdateUserService_positive() throws ServiceException {

        User testUserChanged = new User(1, "admin", "PasswordChanged");
        boolean actualUpdateUserServiceResult = userService.updateUserService(testUserChanged);
        assertEquals(true, actualUpdateUserServiceResult);
        User originalUser = new User(1, "admin", "root");
        userService.updateUserService(originalUser);
    }

    @Test(expected = ServiceException.class)
    public void testUpdateUserService_negative() throws ServiceException {
        User testUserChanged = null;
        userService.updateUserService(testUserChanged);
    }


    @Test
    public void deleteUserService_positive() throws ServiceException {
//        String userName = "testUserName";

        User testUser = new User("testUserName", "testUserPassword");
        userService.registerUserService(testUser);

        boolean actualDeleteUserServiceResult = userService.deleteUserService(testUser.getUserName());
        assertEquals(true, actualDeleteUserServiceResult);

    }

    @Test(expected = ServiceException.class)
    public void deleteUserService_negative() throws ServiceException {
        User testUser = new User(null, null);
        userService.deleteUserService(testUser.getUserName());
    }
}