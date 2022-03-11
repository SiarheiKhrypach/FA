package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.services.formDataCollectors.ProductFormDataCollector;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.ProductService;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.Validator;
import by.tut.ssmt.service.dataProcessors.AcidsProportionListImpl;
import by.tut.ssmt.service.dataProcessors.DataProcessorList;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AddCommand implements Command {

    private ArrayList<Product> products; //to move
    final Validator validator = new Validator(); //to move
//    final DBConnector dbConnector = new DBConnector(); //to move
//    final ProductDaoImpl productDaoImpl = new ProductDaoImpl(dbConnector); //to move
    final DataProcessorList dataProcessorList = new AcidsProportionListImpl(); //to move
    final ProductFormDataCollector dataCollector = new ProductFormDataCollector(); //controller
    private boolean productDoesntExist;

private ServiceFactory serviceFactory =ServiceFactory.getInstance();
private ProductService productService = serviceFactory.getProductService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
            products = (ArrayList<Product>) productService.selectAllService();
            validator.isNotNull(products); // controller - create validator on each layer

            final Product product = dataCollector.collectFormData(request); //controller

            verify(product); //move
            if (productDoesntExist) {
                productService.addService(product); //move
                products.add(product);
            }
            assignAttributes(request);
            postToMainPage(request, response); //controller

        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please enter valid data"); //controller
            request.getRequestDispatcher("index.jsp").forward(request, response); //controller
        }

    }

    private void verify(Product product) {
        productDoesntExist = true;
        for (int i = 0; i < products.size(); i++) {
            if (product.getProductName().equals(products.get(i).getProductName())) {
                productDoesntExist = false; //move
            }
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (productDoesntExist) {
            response.sendRedirect(request.getContextPath() + "/");   //controller
        } else {
            request.setAttribute("message", "The list already has product with such name");  //controller
            request.getRequestDispatcher("index.jsp").forward(request, response); //controller
        }
    }

    private void assignAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        collectProductData(session);
        session.setAttribute("productsAttribute", products);
        setProportion(session);
    }

    private void collectProductData(HttpSession session) {
        products = (ArrayList<Product>) productService.selectAllService();
        validator.isNotNull(products);
        session.setAttribute("productsAttribute", products);
    }

    private void setProportion(HttpSession session) {
//        products = (ArrayList<Product>) productService.selectAllService();
        final String formattedProportion = dataProcessorList.calculate(products);
        validator.isNotNull(formattedProportion);
        session.setAttribute("proportion", formattedProportion);
    }

}

