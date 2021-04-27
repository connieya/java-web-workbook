package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

// Serlvet 3.0 �����ʹ� �ֳ����̼����� ���� ��ġ ������ ������ �� �ִ�.
@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
				// ServletRequest ��ü�� Ŭ���̾�Ʈ�� ��û ������ �ٷ� �� ����Ѵ�.
		
		
		int a = Integer.parseInt(req.getParameter("a"));
		int b = Integer.parseInt(req.getParameter("b"));
		
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter writer = res.getWriter();
		writer.println("a= "+a +", "+"b= "+b +"�� ��� ����Դϴ�.");
		writer.println("a + b = " +(a+b));
		writer.println("a - b = " +(a-b));
		writer.println("a * b = " +(a*b));
		writer.println("a / b = " +((float)a / (float)b ));
		writer.println("a % b = " +(a%b));
	}

}
