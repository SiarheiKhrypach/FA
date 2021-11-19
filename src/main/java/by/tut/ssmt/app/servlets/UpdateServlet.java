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
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

    private Map<Integer, Product> products;
    String noValidName;
    final Validator validator = new EntryValidatorImpl();
    final DataProcessor dataProcessor = new AcidsProportion();


    @Override
    public void init() throws ServletException {
//        products = (ConcurrentHashMap<Integer, Product>) getServletContext().getAttribute("productsInContext");
        final Object products = getServletContext().getAttribute("productsInContext");
        if (products == null) {
            throw new IllegalStateException("Initialization error in AddServlet!");
        } else {
            this.products = (ConcurrentHashMap<Integer, Product>) products;
        }
//        System.out.println(products);
//        System.out.println("!!!!!!!!init in UpdateServlet");

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (validator.validate(req.getParameter("productName"))) {
//            String idString = req.getParameter("id");
//            System.out.println(idString);
            resetData(req);
//            collectProportion(req);
            collectProportionForContext(getServletContext());

//            collectNameValidity(req);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
        else {
            noValidName = "Please enter a valid name";
            req.setAttribute("noValidName", noValidName);
            req.setAttribute("products", products.values());
//            collectProportion(req);
            collectProportionForContext(getServletContext());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String id = req.getParameter("id");
        final Product product = products.get(Integer.parseInt(id));
//        System.out.println("!!!!!!!!doGet in UpdateServlet " + id);
        req.setAttribute("product", product);
        req.getRequestDispatcher("update.jsp").forward(req, resp);
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


        private void resetData(HttpServletRequest req) {
            Product product = getProduct(req);
//            String idString = req.getParameter("id");
        final int id = Integer.valueOf(req.getParameter("id"));
        product.setId(id);
        products.put(id, product);
//        System.out.println("!!!!!!resetData in UpdateServlet " + products);
//        req.setAttribute("products", products.values());
    }

    private Product getProduct(HttpServletRequest req) {
        final String productName = req.getParameter("productName");
        final String omegaThree = req.getParameter("omegaThree");
        final String omegaSix = req.getParameter("omegaSix");
        final String portion = req.getParameter("portions");
        return new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
    }
}
