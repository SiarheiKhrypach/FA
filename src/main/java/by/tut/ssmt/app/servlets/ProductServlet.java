package by.tut.ssmt.app.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        try {
            String url = "jdbc:mysql://localhost/fa_db";
            String username = "root";
            String password = "root";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            LOGGER.info("Call to doGet() successed");
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                writer.println("Connection to FA_DB is successful!");
            }
        } catch (Exception ex) {
            writer.println("Connection failed...");
            writer.println(ex);
        } finally {
            writer.close();
        }
    }
}
