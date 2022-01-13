package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.services.dataProcessors.DataProcessorList;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.formDataCollectors.ProductFormDataCollector;
import org.apache.log4j.spi.RootLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/add")
public class AddServlet extends HttpServlet {

    private ArrayList<Product> products;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    final ProductFormDataCollector dataCollector = new ProductFormDataCollector();
    private boolean productDoesntExist;

    public void init() {
        products = productDao.select();
        validator.isNotNull(products);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            final Product product = dataCollector.collectFormData(req);
            RootLogger log = (RootLogger) getServletContext().getAttribute("log4");
            log.info("Log Test");
            verify(product);
            if (productDoesntExist) {
                productDao.insert(product);
            }
            assignAttributes();
            postToMainPage(req, resp);

        } catch (NullOrEmptyException e) {
            assignAttributes();
            req.setAttribute("message", "Please enter valid data");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

    }

    private void verify(Product product) {
        productDoesntExist = true;
        for (int i = 0; i < products.size(); i++) {
            if (product.getProductName().equals(products.get(i).getProductName())) {
                productDoesntExist = false;
            }
        }
    }

    private void postToMainPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (productDoesntExist) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "The list already has product with such name");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private void assignAttributes() {
        collectProductDataForContext();
        collectProportionForContext();
    }

    private void collectProductDataForContext() {
        products = productDao.select();
        validator.isNotNull(products);
        getServletContext().setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext() {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        getServletContext().setAttribute("proportion", formattedProportion);
    }

}