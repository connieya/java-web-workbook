package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list") // ������ ������� javax.servlet.GenericSevlet Ŭ������ ��� �޴´�.
public class MemberListServlet extends GenericServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		
		// JDBC ��ü �ּҸ� ������ ���� ������ ����
		Connection conn = null; 
		//java.sql.Connection �������̽��� �ֿ� �޼���
		//createStatement() , prepareStatement() , prepateCall()�� SQL ���� �����ϴ� ��ü�� ��ȯ
		//commit(), rollback() Ʈ����� ó���� �����ϴ� �޼���
		Statement stmt = null;
		// Statement �������̽��� �����ͺ��̽��� �����ϴ� �� �ʿ��� �޼��尡 ���ǵǾ� �ִ�
		// �ֿ�޼���� executeQuery().executeUpdate(), execute() , executeBatch()
		ResultSet rs = null;
		
		
		// JDBC API�� ����� �� ���ܰ� �߻��� ��  �ֱ� ������
		try {
			// DriverManager�� �̿��Ͽ� java.sql.Driver �������̽� ����ü ���
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			// MySQL ����̹��� ��� com.mysql.jdbc.Driver Ŭ������ �ش� �������̽��� ������ Ŭ����
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/world", // MySQL ������ ���� ���� -> JDBC URL
					"root",
					"1234"
					);
			stmt = conn.createStatement(); 
			// createStatement()�� ��ȯ�ϴ� ���� java.sql.Statement �������̽� ����ü
			rs = stmt.executeQuery(
					"select mno,mname,email,cre_date from members order by mno asc"
					);
			
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>ȸ�����</title></head>");
			out.println("<body><h1>ȸ�����</h1>");
			out.println("<p><a href='add'>�ű� ȸ��</a></p>");
			// URL �� '/' ���� �����ϸ� ���� ����̰� '/'���� �������� ������ ��� ����Դϴ�.
			
			while(rs.next()) {
				out.println(
						rs.getInt("MNO")+","
						+"<a href='update?no="+rs.getInt("MNO")+"'>"+rs.getString("MNAME")+"</a>"+","
				        +rs.getString("EMAIL")+
						rs.getDate("CRE_DATE")+
						"<a href='delete?no=" + rs.getInt("MNO") + 
						"'>[����]</a><br>");
			}
			out.print("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		}finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
	
	

}
