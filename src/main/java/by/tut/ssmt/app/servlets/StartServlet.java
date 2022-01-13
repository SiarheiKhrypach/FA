package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import org.apache.log4j.spi.RootLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/")
public class StartServlet extends HttpServlet {

    ArrayList<Product> products;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        products = productDao.select();
        validator.isNotNull(products);
        req.setAttribute("productsAttribute", products);
        RootLogger log = (RootLogger)getServletContext().getAttribute("log4");
        log.info("Enter to Servlet");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
