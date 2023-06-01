package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.MenuDao;
import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.ServiceValidator;
import by.tut.ssmt.service.exception.NullOrEmptyException;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao = DaoFactory.getInstance().getMenuDao();
    private final ServiceValidator serviceValidator = new ServiceValidator();

    @Override
    public List<Product> selectAllFromMenuService(String currentUser) throws ServiceException {
        try {
            serviceValidator.isNotNullOrEmpty(currentUser);
            return menuDao.selectAllFromMenuDao(currentUser);
        } catch (DaoException | NullOrEmptyException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Page<Product> findMenuPageService(Page<Product> menuItemPagedRequest) throws ServiceException {
        try {
            serviceValidator.isNotNull(menuItemPagedRequest);
            return menuDao.findMenuPageDao(menuItemPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean addMenuService(MenuItem menuItem) throws ServiceException {
        try {
            serviceValidator.isNotNull(menuItem);
            return menuDao.insertMenuDao(menuItem);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean deleteMenuService(String productName, String userName) throws ServiceException {
        try {
            serviceValidator.isNotNullOrEmpty(productName, userName);
            return menuDao.deleteMenuDao(productName, userName);
        } catch (DaoException | NullOrEmptyException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean bulkPortionChangeMenuService(List<MenuItem> menuList) throws ServiceException {
        try {
            serviceValidator.isNotNull(menuList);
            return menuDao.bulkPortionChangeMenuDao(menuList);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
