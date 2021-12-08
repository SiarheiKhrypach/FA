package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.ProductDB;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.AcidsProportionListImpl;
import by.tut.ssmt.services.DataProcessor;

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
    final DataProcessor dataProcessor = new AcidsProportionListImpl();
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        setProductInitialData(servletContext);
        setUserInitialData(servletContext);
        collectProportionForContext(servletContext);
    }

    private void setUserInitialData(ServletContext servletContext) {
        users = new ArrayList<>();
        final User admin = new User("admin", "pass");
        users.add(admin);
        servletContext.setAttribute("usersInContext", users);
    }

    private void setProductInitialData(ServletContext servletContext) {
        products = ProductDB.select();
        servletContext.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        String formattedProportion = dataProcessor.calculate(products);
        LOGGER.info("proportion: " + formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        products = null;
    }
}
