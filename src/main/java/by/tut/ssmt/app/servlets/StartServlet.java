package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.ProductDB;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet("/")
public class StartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());
    private ArrayList<Product> products;
    Validator validator = new Validator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        products = ProductDB.select();
        validator.isNotNull(products);
        req.setAttribute("productsAttribute", products);
        LOGGER.info("Call to doGet() , Attribute productsAttribute:" + req.getAttribute("productsAttribute"));
        LOGGER.info("Call to doGet() , Attribute productsAttribute:" + getServletContext().getAttribute("productsAttribute"));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
