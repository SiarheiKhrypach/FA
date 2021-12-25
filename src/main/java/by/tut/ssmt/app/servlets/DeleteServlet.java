package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.services.dataProcessors.DataProcessorList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DeleteServlet.class.getName());
    private ArrayList<Product> products;
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl();
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDao.delete(Integer.parseInt(req.getParameter("id")));
        assignAttribute(getServletContext());
        collectProportionForContext(getServletContext());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void assignAttribute(ServletContext servletContext) {
        products = productDao.select();
        validator.isNotNull(products);
        servletContext.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
