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

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

    private ArrayList<Product> products;
    String message;
    final Validator validator = new Validator();
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String id = req.getParameter("id");
        final Product product = ProductDB.selectOne(Integer.parseInt(id));
        validator.isValidData(product);
        req.setAttribute("product", product);
        req.getRequestDispatcher("update.jsp").forward(req, resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resetData(req);
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);

        } catch (NullOrEmptyException e) {
            message = "Please enter a valid name";
            req.setAttribute("message", message);
            req.setAttribute("products", products);
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isValidData(formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    private void resetData(HttpServletRequest req) throws NullOrEmptyException {
        Product product = getProduct(req);
        ProductDB.update(product);
        assignAttribute(getServletContext());
    }

    private void assignAttribute(ServletContext servletContext) {
        products = ProductDB.select();
        validator.isValidData(products);
        servletContext.setAttribute("productsAttribute", products);
    }

    private Product getProduct(HttpServletRequest req) throws NullOrEmptyException {
        final String id = req.getParameter("id");
        final String productName = req.getParameter("productName");
        validator.validate(productName);
        final String omegaThree = req.getParameter("omegaThree");
        validator.validate(omegaThree);
        final String omegaSix = req.getParameter("omegaSix");
        validator.validate(omegaSix);
        final String portion = req.getParameter("portions");
        validator.validate(portion);
        return new Product(Integer.parseInt(id), productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
    }
}
