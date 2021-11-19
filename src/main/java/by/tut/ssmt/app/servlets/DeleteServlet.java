package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.AcidsProportion;
import by.tut.ssmt.services.DataProcessor;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DeleteServlet.class.getName());
    private Map<Integer, Product> products;
    final DataProcessor dataProcessor = new AcidsProportion();
//    String noValidName;


    @Override
    public void init() throws ServletException {
//        products = (ConcurrentHashMap<Integer, Product>) getServletContext().getAttribute("productsInContext");
        final Object products = getServletContext().getAttribute("productsInContext");
        if (products == null) {
            throw new IllegalStateException("Initialization error in AddServlet!");
        } else {
            this.products = (ConcurrentHashMap<Integer, Product>) products;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        products.remove(Integer.valueOf(req.getParameter("id")));
//        req.setAttribute("products", products.values());
//        collectProportion(req);
        collectProportionForContext(getServletContext());

//        collectNameValidity(req);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
//        resp.sendRedirect(req.getContextPath() + "/");

    }

//    private void collectNameValidity(HttpServletRequest req) {
//        noValidName = "";
//        req.setAttribute("noValidName", noValidName);
//    }

//    private void collectProportion(HttpServletRequest req) {
////        double proportion = dataProcessor.calculate(products);
//        String formattedProportion = dataProcessor.calculate(products);
////        String formattedProportion = new DecimalFormat("#0.00").format(proportion);
//        req.setAttribute("proportion", formattedProportion);
//    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessor.calculate(products);
//        String formattedProportion = new DecimalFormat("#0.00").format(proportion);
        servletContext.setAttribute("proportion", formattedProportion);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
