package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.dao.domain.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CalculateCommand implements Command {

    List<Product> products;

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProductService productService = serviceFactory.getProductService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
//        String data = request.getParameter("portions");

//        try {
//            products = productService.selectAllService();
//
//            double totalOmegaSix = 0;
//            String proportion = "";
//            double totalOmegaThree = 0;
//            for (Product product : products) {
//                totalOmegaThree += product.getOmegaThree() * Integer.parseInt(request.getParameter(product.getProductName()));
////            totalOmegaThree += product.getOmegaThree() * product.getPortion();
//                totalOmegaSix += product.getOmegaSix() * product.getPortion();
//                if ((totalOmegaThree != 0)) {
//                    proportion = String.valueOf(new DecimalFormat("#0.00").format(totalOmegaSix / totalOmegaThree));
//                } else {
//                    proportion = "Division by zero - Your totals for Omega3 is null";
//                }
//            }
//
//            request.setAttribute("proportion", proportion);

            String data1 = request.getParameter("ihhkjh");
            String data2 = request.getParameter("gfgj");
            String data3 = request.getParameter("mhgj");
            String result = data1 + data2 + data3;
            request.setAttribute("data", result);
//        response.sendRedirect("/main");
            request.getRequestDispatcher("index.jsp").forward(request, response);

//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }

     }
}
