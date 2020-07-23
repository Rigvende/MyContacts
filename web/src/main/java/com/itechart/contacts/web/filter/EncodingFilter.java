package com.itechart.contacts.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Class for filtering data's characters encoding during request/response data sending.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    private final static String CHARSET = "UTF-8";
    private final static String TYPE = "text/html; charset=UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(CHARSET);
        response.setCharacterEncoding(CHARSET);
        response.setContentType(TYPE);
        System.out.println("Привет");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
