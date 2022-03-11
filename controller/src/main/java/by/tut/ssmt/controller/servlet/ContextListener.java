package by.tut.ssmt.controller.servlet;

import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.dataProcessors.DataProcessorList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener
public class ContextListener implements ServletContextListener {

    private ArrayList<Product> products;
    private ArrayList<User> users;

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();
    final Validator validator = serviceFactory.getValidator();
    final ProductService productService = serviceFactory.getProductService();
    final UserService userService = serviceFactory.getUserService();


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("command", "default");
        setUserInitialData(servletContext);
        setProductInitialData(servletContext);
        setProportion(servletContext);
    }

    private void setUserInitialData(ServletContext servletContext) {
        users = userService.selectService();
        validator.isNotNull(users);
        servletContext.setAttribute("usersInContext", users);
    }

    private void setProductInitialData(ServletContext servletContext) {
        products = (ArrayList<Product>) productService.selectAllService();
        validator.isNotNull(products);
        servletContext.setAttribute("productsAttribute", products);
    }

    private void setProportion(ServletContext servletContext) {
        String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        products = null;
    }
}
