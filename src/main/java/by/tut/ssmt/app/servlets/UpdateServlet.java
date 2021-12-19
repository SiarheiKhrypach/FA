package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.ProductDB;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.services.dataProcessors.DataProcessorList;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.formDataCollectors.FormDataCollector;
import by.tut.ssmt.services.formDataCollectors.ProductFormDataCollector;
import java.util.logging.Logger;


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

    private static final Logger LOGGER = Logger.getLogger(AddServlet.class.getName()) ;
    private ArrayList<Product> products;
    final Validator validator = new Validator();
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    final FormDataCollector dataCollector = new ProductFormDataCollector();
    private boolean productDoesntExist;

    public void init() {
        LOGGER.info("Call to init()");
        products = ProductDB.select();
        validator.isValidData(products);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String id = req.getParameter("id");
        final Product product = ProductDB.selectOne(Integer.parseInt(id));
        validator.isValidData(product);
        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/update.jsp").forward(req, resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resetData(req);
            collectProportionForContext(getServletContext());
            postToMainPage(req, resp);

        } catch (NullOrEmptyException e) {
            assignAttribute();
            req.setAttribute("message", "Please enter a valid name");
            req.setAttribute("products", products);
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private void postToMainPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!productDoesntExist) {
            req.setAttribute("message", "The list already has product with such name");
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void assignAttribute() {
        products = ProductDB.select();
        validator.isValidData(products);
        getServletContext().setAttribute("productsAttribute", products);
    }


    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isValidData(formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    private void resetData(HttpServletRequest req) throws NullOrEmptyException {
        Product product = getProduct(req);
        verify (product);
        ProductDB.update(product);
        assignAttribute(getServletContext());
    }

    private void verify(Product product) {
        productDoesntExist = true;
        for (int i = 0; i < products.size(); i++) {
            if (product.getProductName().equals(products.get(i).getProductName()) && product.getId() != products.get(i).getId()) {
                productDoesntExist = false;
            }
        }
    }

    private void assignAttribute(ServletContext servletContext) {
        products = ProductDB.select();
        validator.isValidData(products);
        servletContext.setAttribute("productsAttribute", products);
    }

    private Product getProduct(HttpServletRequest req) throws NullOrEmptyException {
        final String id = req.getParameter("id");
        validator.isNotNullOrEmpty(id);
        final Product product = (Product) dataCollector.collectFormData(req);
        product.setId(Long.parseLong(id));
        return product;
    }
}
