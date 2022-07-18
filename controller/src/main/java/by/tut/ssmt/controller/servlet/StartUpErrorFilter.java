package by.tut.ssmt.controller.servlet;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class StartUpErrorFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(StartUpErrorFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String message = (String) request.getServletContext().getAttribute("message");
        if (message.equals("error")) {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
