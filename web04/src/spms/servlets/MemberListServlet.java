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

@WebServlet("/member/list") // 서블릿을 만들고자 javax.servlet.GenericSevlet 클래스를 상속 받는다.
public class MemberListServlet extends GenericServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		
		// JDBC 객체 주소를 보관할 참조 변수의 선언
		Connection conn = null; 
		//java.sql.Connection 인터페이스의 주요 메서드
		//createStatement() , prepareStatement() , prepateCall()은 SQL 문을 싱행하는 객체를 반환
		//commit(), rollback() 트랜잭션 처리를 수행하는 메서드
		Statement stmt = null;
		// Statement 인터페이스는 데이터베이스에 질의하는 데 필요한 메서드가 정의되어 있다
		// 주요메서드는 executeQuery().executeUpdate(), execute() , executeBatch()
		ResultSet rs = null;
		
		
		// JDBC API를 사용할 때 예외가 발생할 수  있기 때문에
		try {
			// DriverManager를 이용하여 java.sql.Driver 인터페이스 구현체 등록
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			// MySQL 드라이버의 경우 com.mysql.jdbc.Driver 클래스가 해당 인터페이스를 구현한 클래스
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/world", // MySQL 서버의 접속 정보 -> JDBC URL
					"root",
					"1234"
					);
			stmt = conn.createStatement(); 
			// createStatement()가 반환하는 것은 java.sql.Statement 인터페이스 구현체
			rs = stmt.executeQuery(
					"select mno,mname,email,cre_date from members order by mno asc"
					);
			
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			out.println("<p><a href='add'>신규 회원</a></p>");
			// URL 이 '/' 으로 시작하면 절대 경로이고 '/'으로 시작하지 않으면 상대 경로입니다.
			
			while(rs.next()) {
				out.println(
						rs.getInt("MNO")+","
						+"<a href='update?no="+rs.getInt("MNO")+"'>"+rs.getString("MNAME")+"</a>"+","
				        +rs.getString("EMAIL")+
						rs.getDate("CRE_DATE")+
						"<a href='delete?no=" + rs.getInt("MNO") + 
						"'>[삭제]</a><br>");
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
