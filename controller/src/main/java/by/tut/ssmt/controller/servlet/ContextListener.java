package by.tut.ssmt.controller.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

//    private ArrayList<Product> products;
//    private ArrayList<User> users;

//    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

//    final DataProcessorList dataProcessorList = serviceFactory.getDataProcessorList();
//    final Validator validator = serviceFactory.getValidator();
//    final ProductService productService = serviceFactory.getProductService();
//    final UserService userService = serviceFactory.getUserService();


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("command", "default");

//        try {
//            setUserInitialData(servletContext);
//            setProductInitialData(servletContext);
//
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            servletContext.setAttribute("message", "error");
////            throw new RuntimeException(e);
////            servletContext.getRequestDispatcher("/WEB-INF/error.jsp").forward(request);
//        }
//        setProportion(servletContext);

    }

//        private void setUserInitialData(ServletContext servletContext) {
//            users = userService.selectAllService();
//            validator.isNotNull(users);
//            servletContext.setAttribute("usersInContext", users);
//        }
//
//        private void setProductInitialData(ServletContext servletContext) throws ServiceException {
//            products = (ArrayList<Product>) productService.selectAllService();
//            validator.isNotNull(products);
//            servletContext.setAttribute("productsAttribute", products);
//        }
//
//        private void setProportion(ServletContext servletContext) {
//            String formattedProportion = dataProcessorList.calculate(products);
//            validator.isNotNull(formattedProportion);
//            servletContext.setAttribute("proportion", formattedProportion);
//        }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        products = null;
    }
}
