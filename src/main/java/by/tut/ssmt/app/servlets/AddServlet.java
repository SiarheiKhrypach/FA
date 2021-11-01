package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.EntryValidatorImpl;
import by.tut.ssmt.services.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AddServlet extends HttpServlet {

    private AtomicInteger id;
    private List<Product> products = new ArrayList<Product>();

    Validator validator = new EntryValidatorImpl();


    public void init() throws ServletException {
        id = new AtomicInteger(1);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (validator.validate(req.getParameter("productName"))) {
            String productName = req.getParameter("productName");

            String omegaThree = req.getParameter("omegaThree");
            String omegaSix = req.getParameter("omegaSix");
            String portion = req.getParameter("portions");
            Product product = new Product(productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
            int id = this.id.getAndIncrement();
            product.setId(id);
            products.add(product);

            double proportion = getProportion();

            req.setAttribute("products", products);
            req.setAttribute("proportion", proportion);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
//        req.getRequestDispatcher("test.jsp").forward(req, resp);
//        resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private double getProportion() {
        double totalOmegaSix = getTotalOmegaSix();
        double totalOmegaThree = getTotalOmegaThree();
        return totalOmegaSix / totalOmegaThree;
    }

    private double getTotalOmegaThree() {
        double totalOmegaThree = 0;
        for(int i = 0; i<products.size(); i++) {
            totalOmegaThree += products.get(i).getOmegaThree() * products.get(i).getPortion();
        }
        return totalOmegaThree;
    }

    private double getTotalOmegaSix() {
        double totalOmegaSix = 0;
        for(int i = 0; i<products.size(); i++) {
            totalOmegaSix += products.get(i).getOmegaSix() * products.get(i).getPortion();
        }
        return totalOmegaSix;
    }
}
