package by.tut.ssmt.app.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class CalculationServlet extends HttpServlet {

    ArrayList finalList;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        finalList = (ArrayList) req.getAttribute("products");
        System.out.println("!!!!!!!!!!!doGet in CalculateServlet!!!!!!!!!!");
        System.out.println(finalList);
    }
}
