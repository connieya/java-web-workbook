package com.example.java_servlet.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter) throws IOException, ServletException {
        request.setCharacterEncoding(config.getInitParameter("encoding"));

        nextFilter.doFilter(request,response);
    }
}
