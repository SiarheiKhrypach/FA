package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.exception.ServiceException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MenuServiceImplTest {

    private final MenuService menuService = ServiceFactory.getInstance().getMenuService();

    @Test
    public void testSelectAllFromMenuServicePositive() throws ServiceException {
        String testUser = "admin";
        final List<Product> expectedMenuItems = Arrays.asList(
                new Product(1179, "lopp", 34, 2, 1)
        );
        final int expectedLength = expectedMenuItems.size();

        List<Product> actualMenuItems = menuService.selectAllFromMenuService(testUser);
        assertEquals(expectedLength, actualMenuItems.size());
        assertEquals(expectedMenuItems, actualMenuItems);
    }

    @Test
    public void testFindMenuPageServicePositive() throws ServiceException {
        final Page<Product> menuItemPageRequest = new Page<>();
        menuItemPageRequest.setPageNumber(1);
        menuItemPageRequest.setLimit(5);
        menuItemPageRequest.setCurrentUser("admin");
        menuItemPageRequest.setOrderBy("product_name ASC");
        menuItemPageRequest.setFilter("'%'");

        Page<Product> expectedPagedMenuItemList = new Page(1, 1, 5, null, null, null, Arrays.asList(new Product(1179, "lopp", 34, 2, 1)));
        Page<Product> actualPagedMenuItemList = menuService.findMenuPageService(menuItemPageRequest);
        assertEquals(expectedPagedMenuItemList, actualPagedMenuItemList);
    }

    @Test(expected = ServiceException.class)
    public void testFindMenuPageServiceNegative() throws ServiceException {
        final Page<Product> menuItemPageRequest = null;
        menuService.findMenuPageService(menuItemPageRequest);
    }

    @Test
    public void testAddMenuServicePositive() throws ServiceException {
        MenuItem testMenuItem = new MenuItem("admin", 1179, 2);
        boolean actualAddMenuServiceResult = menuService.addMenuService(testMenuItem);
        assertEquals(true, actualAddMenuServiceResult);

        Page<Product> expectedPagedMenuItemList = new Page(1, 1, 5, null, null, null, Arrays.asList(new Product(1179, "lopp", 34, 2, 3)));
        final Page<Product> menuItemPageRequest = new Page<>();
        menuItemPageRequest.setPageNumber(1);
        menuItemPageRequest.setLimit(5);
        menuItemPageRequest.setCurrentUser("admin");
        menuItemPageRequest.setOrderBy("product_name ASC");
        menuItemPageRequest.setFilter("'%'");
        Page<Product> actualPagedMenuItemList = menuService.findMenuPageService(menuItemPageRequest);
        assertEquals(expectedPagedMenuItemList, actualPagedMenuItemList);

        List<MenuItem> menuItemChangedBack = Arrays.asList(
                new MenuItem("admin", 1179, 1)
        );
        menuService.bulkPortionChangeMenuService(menuItemChangedBack);
    }

    @Test(expected = ServiceException.class)
    public void testAddMenuServiceNegative() throws ServiceException {
        MenuItem testMenuItem = null;
        menuService.addMenuService(testMenuItem);
    }

    @Test
    public void testDeleteMenuServicePositive() throws ServiceException {
        String testProductName = "lopp";
        String testUser = "admin";
        boolean actualDeleteMenuServiceResult = menuService.deleteMenuService(testProductName, testUser);
        assertEquals(true, actualDeleteMenuServiceResult);

        MenuItem menuItemAddedBack = new MenuItem("admin", 1179, 1);
        menuService.addMenuService(menuItemAddedBack);
    }

    @Test (expected = ServiceException.class)
    public void testDeleteMenuServiceNegative() throws ServiceException {
        String testProductName = null;
        String testUser = "admin";
        menuService.deleteMenuService(testProductName, testUser);
    }

    @Test
    public void testBulkPortionChangeMenuServicePositive() throws ServiceException {
        List<MenuItem> testMenuItem = Arrays.asList(
                new MenuItem("admin", 1179, 5)
        );
        menuService.bulkPortionChangeMenuService(testMenuItem);
        boolean expectedBulkPortionChangeMenuServiceResult = true;
        assertEquals(true, expectedBulkPortionChangeMenuServiceResult);

        Page<Product> expectedPagedMenuItemList = new Page(1, 1, 5, null, null, null, Arrays.asList(new Product(1179, "lopp", 34, 2, 5)));
        final Page<Product> menuItemPageRequest = new Page<>();
        menuItemPageRequest.setPageNumber(1);
        menuItemPageRequest.setLimit(5);
        menuItemPageRequest.setCurrentUser("admin");
        menuItemPageRequest.setOrderBy("product_name ASC");
        menuItemPageRequest.setFilter("'%'");
        Page<Product> actualPagedMenuItemList = menuService.findMenuPageService(menuItemPageRequest);
        assertEquals(expectedPagedMenuItemList, actualPagedMenuItemList);

        List<MenuItem> menuItemChangedBack = Arrays.asList(
                new MenuItem("admin", 1179, 1)
        );
        menuService.bulkPortionChangeMenuService(testMenuItem);

    }

    @Test (expected = ServiceException.class)
    public void testBulkPortionChangeMenuServiceNegative() throws ServiceException {
        List<MenuItem> testMenuItem = Arrays.asList(
                new MenuItem(null, 1179, 5)
        );
        menuService.bulkPortionChangeMenuService(testMenuItem);
    }
}