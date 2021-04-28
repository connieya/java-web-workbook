package web02.lesson02.post;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Ŭ���̾�Ʈ ���� ������ ���� ���ڼ� ����
		req.setCharacterEncoding("UTF-8");
		String id = req.getParameter("id");
		
		// Ŭ���̾�Ʈ�� ����ϱ� ���� �غ��Ѵ�.
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		out.println("<html><body>");
		out.println("<h1>�α��� ���</h1>");
		out.println(id+"���� ȯ���մϴ�.");
		out.println("</body></html>");
	}

}
