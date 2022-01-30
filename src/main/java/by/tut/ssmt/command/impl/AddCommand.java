package by.tut.ssmt.command.impl;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.command.Command;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.services.dataProcessors.DataProcessorList;
import by.tut.ssmt.services.exceptions.NullOrEmptyException;
import by.tut.ssmt.services.formDataCollectors.ProductFormDataCollector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AddCommand implements Command {

    private ArrayList<Product> products;
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    final ProductFormDataCollector dataCollector = new ProductFormDataCollector();
    private boolean productDoesntExist;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        products = productDao.select();
        validator.isNotNull(products);
        try {
            final Product product = dataCollector.collectFormData(req);
            verify(product);
            if (productDoesntExist) {
                productDao.insert(product);
            }
            assignAttributes(req);
            postToMainPage(req, resp);

        } catch (NullOrEmptyException e) {
            assignAttributes(req);
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

    private void assignAttributes(HttpServletRequest req) {
        HttpSession session = req.getSession();
        collectProductData(session);
        collectProportion(session);
    }

    private void collectProductData(HttpSession session) {
        products = productDao.select();
        validator.isNotNull(products);
        session.setAttribute("productsAttribute", products);
    }

    private void collectProportion(HttpSession session) {
        products = productDao.select();
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        session.setAttribute("proportion", formattedProportion);
    }

}

