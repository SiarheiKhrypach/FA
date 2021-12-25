package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.DAO.UserDao;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.services.dataProcessors.DataProcessorList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

    private ArrayList<Product> products;
    private ArrayList<User> users;
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);
    final UserDao userDao = new UserDao(dbConnector);


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        setUserInitialData(servletContext);
        setProductInitialData(servletContext);
        collectProportionForContext(servletContext);
    }

    private void setUserInitialData(ServletContext servletContext) {
        users = userDao.select();
        validator.isNotNull(users);
        servletContext.setAttribute("usersInContext", users);
    }

    private void setProductInitialData(ServletContext servletContext) {
        products = productDao.select();
        validator.isNotNull(products);
        servletContext.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        LOGGER.info("proportion: " + formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        products = null;
    }
}
