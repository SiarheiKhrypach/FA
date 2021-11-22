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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebServlet("/")
public class StartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());
    private Map<Integer, Product> products;
    final DataProcessor dataProcessor = new AcidsProportion();


    public void init() throws ServletException {
        LOGGER.info("Call to init()");
        final Object products = getServletContext().getAttribute("productsInContext");
        if (products == null) {
            throw new IllegalStateException("Initialization error in StartServlet!");
        } else {
            this.products = (ConcurrentHashMap<Integer, Product>) products;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        collectProportionForContext(getServletContext());
        req.setAttribute("products", products.values());
        LOGGER.info("Call to doGet() , Attribute products:" + req.getAttribute("products"));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void collectProportionForContext(ServletContext servletContext) {
        final String formattedProportion = dataProcessor.calculate(products);
        servletContext.setAttribute("proportion", formattedProportion);
    }
}
