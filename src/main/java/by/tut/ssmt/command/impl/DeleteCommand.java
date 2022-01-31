package by.tut.ssmt.command.impl;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.command.Command;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.services.dataProcessors.DataProcessorList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteCommand implements Command {

    private ArrayList<Product> products;
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDao.delete(Integer.parseInt(req.getParameter("productId")));
        assignAttribute(req);
        collectProportionForContext(req);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void assignAttribute(HttpServletRequest req) {
        products = productDao.select();
        validator.isNotNull(products);
        req.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(HttpServletRequest req) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        req.setAttribute("proportion", formattedProportion);
    }
}
