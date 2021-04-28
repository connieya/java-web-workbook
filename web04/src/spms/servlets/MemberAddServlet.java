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
	// HttpServlet 클래스를 상속받게 되면 service() 대신 doGet()이나 doPost()를 정의한다.
	// HttpServlet은 GenericServlet 클래스의 하위 클래스이다.
	// 즉 , HttpServlet -> GenericServlet -> Servlet
	
	// 다시 한번 강조하자면 서블릿 컨테이너는 Servlet 규칙에 정의된 메서드를 호출하기 때문에, 
	// 서블릿 객체는 반드시 Servlet 인터페이스를 구현해야 한다는 것이다.
	
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
		
		// 클라이언트 요청이 들엉묜, 첫째로 상속받은 HttpServlet의 service() 메서드가 호출되고, 둘째로
		// service()는 클라이언트 요청 방식에 따라 doGet()이나 doPost(),doPut() 등의 메서드를 호출한다.
		
	}
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//CharacterEncodingFilter에서 처리
		//request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/world", //JDBC URL
					"root",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			// getParamether() 메서드는 기본적으로 매개변수의 값이 ISO-Latin-1로 인코딩되었다고 가정한다.
			
			stmt.executeUpdate();
			
			// 리다이렉트를 이용한 리프레시
			response.sendRedirect("list");
			// '/'로 시작하지 않기 때문에 상대 주소로 계산
			// 즉, URL은 /member/list 이다.
			
			
			//작업 결과를 출력 한 후 다른 페이지로 이동할 때는 리프래시
			//작업 결과를 출력하지 않고 다른 페이지로 이동 리다이렉트로 처리
			
			// 위에서 바로 list url를 요청하기 때문에 아래 코드는 당연히 출력되지 않는다.
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title>");
			// HTML의 meta 태그를 이용한 리프레시
			out.println("<meta http-equiv='Refresh' content='2; url=list'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다!</p>");
			out.println("</body></html>");
			
			// 리프래시 정보를 응답 헤더에 추가
			//response.addHeader("Refresh", "1;url=list");
			// addHeader 메서드는 HTTP 응답 정보에 헤더를 추가하는 메서드이다.
			
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
