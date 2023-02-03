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
            return menuDao.selectFromMenuDao(currentUser);
        } catch (DaoException | NullOrEmptyException e) {
            throw new ServiceException();
        }
    }

    @Override
    public Page<Product> findPageService(Page<Product> menuItemPagedRequest) throws ServiceException {
        try {
            serviceValidator.isNotNull(menuItemPagedRequest);
            return menuDao.findPageDao(menuItemPagedRequest);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addService(MenuItem menuItem) throws ServiceException {
        try {
            serviceValidator.isNotNull(menuItem);
            return menuDao.insertDao(menuItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean deleteService(String productName, String userName) throws ServiceException {
        try {
            serviceValidator.isNotNullOrEmpty(productName, userName);
            return menuDao.deleteDao(productName, userName);
        } catch (DaoException | NullOrEmptyException e) {
            throw new ServiceException();
        }
    }

    @Override
    public boolean bulkPortionChangeService(List<MenuItem> menuList) throws ServiceException {
        try {
            serviceValidator.isNotNull(menuList);
            return menuDao.bulkPortionChangeDao(menuList);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
