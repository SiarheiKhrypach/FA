package by.tut.ssmt.controller.servlet;


import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String contentType = servletRequest.getContentType();
        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE)) {
            servletRequest.setCharacterEncoding("UTF-8");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
