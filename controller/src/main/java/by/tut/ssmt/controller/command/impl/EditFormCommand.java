package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.dao.DAO.DBConnector;
import by.tut.ssmt.dao.DAO.ProductDaoImpl;
import by.tut.ssmt.dao.repository.entities.Product;
import by.tut.ssmt.service.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditFormCommand extends FormsAccessCommand {

    private static final Logger LOGGER = Logger.getLogger(FormsAccessCommand.class.getName());
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDaoImpl productDaoImpl = new ProductDaoImpl(dbConnector);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String productId = request.getParameter("productId");
        final Product product = productDaoImpl.selectOne(Integer.parseInt(productId));
        validator.isNotNull(product);
        request.setAttribute("product", product);
        super.execute(request, response);
    }
}
