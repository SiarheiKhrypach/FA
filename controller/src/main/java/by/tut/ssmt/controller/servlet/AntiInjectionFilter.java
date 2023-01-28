package by.tut.ssmt.controller.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


import static by.tut.ssmt.controller.util.ControllerConstants.*;

public class AntiInjectionFilter implements Filter {

    private static final String DOES_NOT_CONTAIN = "^((?!<|>|script).)*$";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        for (String[] v : params.values()) {
            sb.append(v[0]);
        }
        if (sb.toString().trim().matches(DOES_NOT_CONTAIN)) {
            filterChain.doFilter(request, response);
        } else {
            servletRequest.setAttribute(MESSAGE, "Attempt of injection!");
            servletRequest.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
