package com.example.java_servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CalculatorServlet extends GenericServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        int a = Integer.parseInt(request.getParameter("a"));
        int b= Integer.parseInt(request.getParameter("b"));

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("a="+a+",b ="+b+"의 계산 결과입니다.");
        writer.println("a + b = " +(a+b)+"의 계산 결과입니다.");
    }
}
