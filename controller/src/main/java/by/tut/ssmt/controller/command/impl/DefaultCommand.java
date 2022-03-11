package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.ProductDaoImpl;
import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.Validator;
import org.apache.log4j.Logger;

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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        products = productDaoImpl.select();
        validator.isNotNull(products);
        request.setAttribute("productsAttribute", products);
        LOGGER.info("Request uri" + request.getRequestURI());
//        System.out.println(request.getRequestURI());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
