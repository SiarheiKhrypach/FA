package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
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
    ArrayList<Product> products;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        products = productDao.select();
        validator.isNotNull(products);
        req.setAttribute("productsAttribute", products);
        LOGGER.info("Call to doGet() , Attribute productsAttribute:" + req.getAttribute("productsAttribute"));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
