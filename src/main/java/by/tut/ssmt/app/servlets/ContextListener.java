package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.repository.entities.User;
import by.tut.ssmt.services.AcidsProportion;
import by.tut.ssmt.services.DataProcessor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

    private Map<Integer, Product> products;
    private ArrayList<User> users;
    final DataProcessor dataProcessor = new AcidsProportion();
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final ServletContext servletContext = servletContextEvent.getServletContext();
        setProductInitialData(servletContext);
        setUserInitialData(servletContext);
        collectProportionForContext(servletContext);
    }

    private void setUserInitialData(ServletContext servletContext) {
        users = new ArrayList<User>();
        final User admin = new User("admin", "pass");
        users.add(admin);
        servletContext.setAttribute("usersInContext", users);
    }

    private void setProductInitialData(ServletContext servletContext) {
        products  = new ConcurrentHashMap<Integer, Product>();
        final Product oliveOil = new Product("olive oil", 34 , 23,1);
        oliveOil.setId(1);
        this.products.put(1, oliveOil);
        servletContext.setAttribute("productsInContext", products);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        String formattedProportion = dataProcessor.calculate(products);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        products = null;
    }
}
