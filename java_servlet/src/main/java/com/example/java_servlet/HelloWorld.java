package com.example.java_servlet;

import javax.servlet.*;
import java.io.IOException;

public class HelloWorld implements Servlet {
    ServletConfig config;
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init() 호출됨");
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
