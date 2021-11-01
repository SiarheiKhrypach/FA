package by.tut.ssmt.app.servlets;

import by.tut.ssmt.repository.entities.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StartServlet extends HttpServlet {

    private AtomicInteger id;
    private List<Product> products = new ArrayList<Product>();

    public void init() throws ServletException {
        id = new AtomicInteger(1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String productName = req.getParameter("productName");
////        double omegaThree = Double.parseDouble(req.getParameter("omegaThree"));
//        String omegaThree = req.getParameter("omegaThree");
////        double omegaSix = Double.parseDouble(req.getParameter("omegaSix"));
//        String omegaSix = req.getParameter("omegaSix");
////        int portion = Integer.parseInt(req.getParameter("portions"));
//        String portion = req.getParameter("portions");
//        System.out.println("!!!!!!!!!!!!!!!!doPost in StartServlet!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        Product product = new Product (productName, Double.parseDouble(omegaThree), Double.parseDouble(omegaSix), Integer.parseInt(portion));
//        int id = this.id.getAndIncrement();
//        product.setId(id);
//        products.add(product);
//        req.setAttribute("products", portion);
//        resp.sendRedirect(req.getContextPath() + "/");
////        doGet(req, resp);
//    }

}
