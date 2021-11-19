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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@WebServlet("/")
public class StartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(StartServlet.class.getName());
//    private AtomicInteger id;
    private Map<Integer, Product> products; //Убрать?
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

//        collectProportion(req);
        collectProportionForContext(getServletContext());

        req.setAttribute("products", products.values());
        LOGGER.info("Call to doGet() , Attribute products:" + req.getAttribute("products"));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
//        getServletContext().getRequestDispatcher("index.jsp").forward(req, resp);

    }

//    private void collectProportion(HttpServletRequest req) {
//        double proportion = dataProcessor.calculate(products);
//        String formattedProportion = new DecimalFormat("#0.00").format(proportion);
//        req.setAttribute("proportion", formattedProportion);
//        LOGGER.info("Call to collectProportion() , Proportion:" + req.getAttribute("proportion"));
//    }
    private void collectProportionForContext(ServletContext servletContext) {
//        double proportion = dataProcessor.calculate(products);
        final String formattedProportion = dataProcessor.calculate(products);
//        String formattedProportion = new DecimalFormat("#0.00").format(proportion);
        servletContext.setAttribute("proportion", formattedProportion);
    }

}
