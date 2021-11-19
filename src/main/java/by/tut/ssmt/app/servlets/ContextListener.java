package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.AcidsProportion;
import by.tut.ssmt.services.DataProcessor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

//    private ArrayList products;
    private Map<Integer, Product> products;
    final DataProcessor dataProcessor = new AcidsProportion();
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final ServletContext servletContext = servletContextEvent.getServletContext();
//        final ServletContext servletContext = getSession().getServletContext();
        products  = new ConcurrentHashMap<Integer, Product>();
//        servletContext.setAttribute("products", products);
        final Product oliveOil = new Product("olive oil", 34 , 23,1);
        oliveOil.setId(1);
        this.products.put(1, oliveOil);
        servletContext.setAttribute("productsInContext", products);
        collectProportionForContext(servletContext);
    }

    private void collectProportionForContext(ServletContext servletContext) {
//        double proportion = dataProcessor.calculate(products);
        String formattedProportion = dataProcessor.calculate(products);
//        String formattedProportion = new DecimalFormat("#0.00").format(proportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        products = null;
    }
}
