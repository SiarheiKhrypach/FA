package by.tut.ssmt.command.impl;

import by.tut.ssmt.DAO.DBConnector;
import by.tut.ssmt.DAO.ProductDao;
import by.tut.ssmt.repository.entities.Product;
import by.tut.ssmt.services.Validator;
import by.tut.ssmt.services.exceptions.ControllerException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditFormCommand extends FormsAccessCommand {

    private static final Logger LOGGER = Logger.getLogger(FormsAccessCommand.class.getName());
    final Validator validator = new Validator();
    final DBConnector dbConnector = new DBConnector();
    final ProductDao productDao = new ProductDao(dbConnector);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, ServletException, IOException {
        final String productId = request.getParameter("productId");
        final Product product = productDao.selectOne(Integer.parseInt(productId));
        validator.isNotNull(product);
        request.setAttribute("product", product);
        super.execute(request, response);
    }
}
