package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.ProductDaoImpl;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class DefaultCommand implements Command {

    ArrayList<Product> products;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDaoImpl productDaoImpl = new ProductDaoImpl(dbConnector);
    public static final Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            products = productDaoImpl.select();
        } catch (DaoException e) {
            e.printStackTrace(); //todo delete
            throw new ControllerException(e);

        }
        validator.isNotNull(products);
        ServletContext servletContext = request.getServletContext();
//        servletContext.setAttribute("productsAttribute", products);
        request.getSession().setAttribute("productsAttribute", products);
//        ArrayList newProducts = (ArrayList) servletContext.getAttribute("productsAttribute");
//        LOGGER.info("newProducts" + newProducts);
//        LOGGER.info("Request uri" + request.getRequestURI());
//        System.out.println(request.getRequestURI());
//        servletContext.getRequestDispatcher("index.jsp").forward(request, response);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
