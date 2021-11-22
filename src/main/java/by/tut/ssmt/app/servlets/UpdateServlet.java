package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.AcidsProportion;
import by.tut.ssmt.services.DataProcessor;
import by.tut.ssmt.services.EntryValidatorImpl;
import by.tut.ssmt.services.Validator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

    private Map<Integer, Product> products;
    String message;
    final Validator validator = new EntryValidatorImpl();
    final DataProcessor dataProcessor = new AcidsProportion();


    @Override
    public void init() throws ServletException {
        final Object products = getServletContext().getAttribute("productsInContext");
        if (products == null) {
            throw new IllegalStateException("Initialization error in AddServlet!");
        } else {
            this.products = (ConcurrentHashMap<Integer, Product>) products;
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (validator.validate(req.getParameter("productName"))) {
            resetData(req);
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
        else {
            message = "Please enter a valid name";
            req.setAttribute("message", message);
            req.setAttribute("products", products.values());
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String id = req.getParameter("id");
        final Product product = products.get(Integer.parseInt(id));
        req.setAttribute("product", product);
        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessor.calculate(products);
        servletContext.setAttribute("proportion", formattedProportion);
    }

        private void resetData(HttpServletRequest req) {
            Product product = getProduct(req);
        final int id = Integer.valueOf(req.getParameter("id"));
        product.setId(id);
        products.put(id, product);
    }

    private Product getProduct(HttpServletRequest req) {
        final String productName = req.getParameter("productName");
        final String omegaThree = req.getParameter("omegaThree");
        final String omegaSix = req.getParameter("omegaSix");
        final String portion = req.getParameter("portions");
        return new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
    }
}
