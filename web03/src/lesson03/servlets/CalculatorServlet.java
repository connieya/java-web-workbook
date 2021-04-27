package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

// Serlvet 3.0 사양부터는 애노테이션으로 서블릿 배치 정보를 설정할 수 있다.
@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
				// ServletRequest 객체는 클라이언트의 요청 정보를 다룰 때 사용한다.
		
		
		int a = Integer.parseInt(req.getParameter("a"));
		int b = Integer.parseInt(req.getParameter("b"));
		
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter writer = res.getWriter();
		writer.println("a= "+a +", "+"b= "+b +"의 계산 결과입니다.");
		writer.println("a + b = " +(a+b));
		writer.println("a - b = " +(a-b));
		writer.println("a * b = " +(a*b));
		writer.println("a / b = " +((float)a / (float)b ));
		writer.println("a % b = " +(a%b));
	}

}
