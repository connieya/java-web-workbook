package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			Member member =
					memberDao.exist(request.getParameter("email"), request.getParameter("password"));
			if(member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				 response.sendRedirect("../member/list");
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServletException(e);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}
}
