package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.MenuDao;
import by.tut.ssmt.dao.domain.MenuItem;
import by.tut.ssmt.dao.domain.Page;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.MenuService;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao = DaoFactory.getInstance().getMenuDao();


    @Override
    public List<Product> selectAllFromMenuService(String currentUser) throws ServiceException {
        try {
            return menuDao.selectFromMenuDao(currentUser);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public Page<Product> findPageService(Page<Product> menuItemPagedRequest) throws ServiceException {
        try {
            return menuDao.findPageDao(menuItemPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addService(MenuItem menuItem) throws ServiceException {
        try {
            menuDao.insertDao(menuItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteService(String productName, String userName) throws ServiceException {
        try {
            menuDao.deleteDao(productName, userName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void portionChangeService(MenuItem menuItem) throws ServiceException {
        try {
            menuDao.portionChangeDao(menuItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void bulkPortionChangeService (List <MenuItem> menuList) throws ServiceException {
        try {
            menuDao.bulkPortionChangeDao(menuList);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
