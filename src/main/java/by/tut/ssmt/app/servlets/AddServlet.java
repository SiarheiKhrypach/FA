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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddServlet.class.getName());
    private AtomicInteger id;
    private Map<Integer, Product> products;
    String message;
    final Validator validator = new EntryValidatorImpl();
    final DataProcessor dataProcessor = new AcidsProportion();

    public void init() {
        LOGGER.info("Call to init()");
        id = new AtomicInteger(2);
        final Object products = getServletContext().getAttribute("productsInContext");
        if (products == null) {
            throw new IllegalStateException("Initialization error in AddServlet!");
        } else {
            this.products = (ConcurrentHashMap<Integer, Product>) products;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (    validator.validate(req.getParameter("productName")) &&
                validator.validate(req.getParameter("omegaThree")) &&
                validator.validate(req.getParameter("omegaSix")) &&
                validator.validate(req.getParameter("portions"))) {
            collectData(req);
            collectProportionForContext(getServletContext());
            resp.sendRedirect(req.getContextPath() + "/");

        } else {
            message = "Please enter valid data";
            req.setAttribute("message", message);
            req.setAttribute("products", products.values());
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            LOGGER.warning(message);
        }
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessor.calculate(products);
        servletContext.setAttribute("proportion", formattedProportion);
    }

    private void collectData(HttpServletRequest req) {
        final String productName = req.getParameter("productName");
        final String omegaThree = req.getParameter("omegaThree");
        final String omegaSix = req.getParameter("omegaSix");
        final String portion = req.getParameter("portions");
        final Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
        int id = this.id.getAndIncrement();
        product.setId(id);
        products.put(id, product);
    }
}
