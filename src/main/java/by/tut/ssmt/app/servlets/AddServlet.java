package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.ProductDB;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.AcidsProportionListImpl;
import by.tut.ssmt.services.DataProcessorList;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddServlet.class.getName());
    private ArrayList<Product> products;
    String message;
    final Validator validator = new Validator();
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();

    public void init() {
        LOGGER.info("Call to init()");
        products = ProductDB.select();
        validator.isValidData(products);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            collectDataForDB(req);
            assignAttribute(getServletContext());
            collectProportionForContext(getServletContext());
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (NullOrEmptyException e) {
            message = "Please enter valid data";
            getServletContext().setAttribute("message", message);
            assignAttribute(getServletContext());
            collectProportionForContext(getServletContext());
            resp.sendRedirect(req.getContextPath() + "/");
            LOGGER.warning(message);
        }

    }

    private void assignAttribute(ServletContext servletContext) {
        products = ProductDB.select();
        validator.isValidData(products);
        LOGGER.info("Content of products, call to init(): " + products);
        servletContext.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isValidData(formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    private void collectDataForDB(HttpServletRequest req) throws NullOrEmptyException {
        final String productName = req.getParameter("productName");
        validator.validate(productName);
        final String omegaThree = req.getParameter("omegaThree");
        validator.validate(omegaThree);
        final String omegaSix = req.getParameter("omegaSix");
        validator.validate(omegaSix);
        final String portion = req.getParameter("portions");
        validator.validate(portion);
        final Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
        ProductDB.insert(product);
    }
}