package by.tut.ssmt.app.servlets;

import by.tut.ssmt.DAO.ProductDB;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.AcidsProportionListImpl;
import by.tut.ssmt.services.DataProcessor;

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
    final DataProcessor dataProcessor = new AcidsProportionListImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDB.delete(Integer.parseInt(req.getParameter("id")));
        assignAttribute(getServletContext());
        collectProportionForContext(getServletContext());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void assignAttribute(ServletContext servletContext) {
        products = ProductDB.select();
        servletContext.setAttribute("productsAttribute", products);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessor.calculate(products);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
