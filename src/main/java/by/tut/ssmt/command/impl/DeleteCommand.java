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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productDao.delete(Integer.parseInt(request.getParameter("productId")));
        assignAttribute(request);
        collectProportionForContext(request);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void assignAttribute(HttpServletRequest request) {
        products = productDao.select();
        validator.isNotNull(products);
        request.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(HttpServletRequest request) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        request.setAttribute("proportion", formattedProportion);
    }
}
