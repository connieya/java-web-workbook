package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	// HttpServlet Ŭ������ ��ӹް� �Ǹ� service() ��� doGet()�̳� doPost()�� �����Ѵ�.
	// HttpServlet�� GenericServlet Ŭ������ ���� Ŭ�����̴�.
	// �� , HttpServlet -> GenericServlet -> Servlet
	
	// �ٽ� �ѹ� �������ڸ� ���� �����̳ʴ� Servlet ��Ģ�� ���ǵ� �޼��带 ȣ���ϱ� ������, 
	// ���� ��ü�� �ݵ�� Servlet �������̽��� �����ؾ� �Ѵٴ� ���̴�.
	
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>ȸ�� ���</title></head>");
		out.println("<body><h1>ȸ�� ���</h1>");
		out.println("<form action='add' method='post'>");
		out.println("�̸�: <input type='text' name='name'><br>");
		out.println("�̸���: <input type='text' name='email'><br>");
		out.println("��ȣ: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='�߰�'>");
		out.println("<input type='reset' value='���'>");
		out.println("</form>");
		out.println("</body></html>");
		
		// Ŭ���̾�Ʈ ��û�� �����, ù°�� ��ӹ��� HttpServlet�� service() �޼��尡 ȣ��ǰ�, ��°��
		// service()�� Ŭ���̾�Ʈ ��û ��Ŀ� ���� doGet()�̳� doPost(),doPut() ���� �޼��带 ȣ���Ѵ�.
		
	}
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//CharacterEncodingFilter���� ó��
		//request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/world", //JDBC URL
					"root",	// DBMS ����� ���̵�
					"1234");	// DBMS ����� ��ȣ
			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			// getParamether() �޼���� �⺻������ �Ű������� ���� ISO-Latin-1�� ���ڵ��Ǿ��ٰ� �����Ѵ�.
			
			stmt.executeUpdate();
			
			// �����̷�Ʈ�� �̿��� ��������
			response.sendRedirect("list");
			// '/'�� �������� �ʱ� ������ ��� �ּҷ� ���
			// ��, URL�� /member/list �̴�.
			
			
			//�۾� ����� ��� �� �� �ٸ� �������� �̵��� ���� ��������
			//�۾� ����� ������� �ʰ� �ٸ� �������� �̵� �����̷�Ʈ�� ó��
			
			// ������ �ٷ� list url�� ��û�ϱ� ������ �Ʒ� �ڵ�� �翬�� ��µ��� �ʴ´�.
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>ȸ����ϰ��</title>");
			// HTML�� meta �±׸� �̿��� ��������
			out.println("<meta http-equiv='Refresh' content='2; url=list'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>��� �����Դϴ�!</p>");
			out.println("</body></html>");
			
			// �������� ������ ���� ����� �߰�
			//response.addHeader("Refresh", "1;url=list");
			// addHeader �޼���� HTTP ���� ������ ����� �߰��ϴ� �޼����̴�.
			
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
