package spms.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spms.dao.MemberDao;
import spms.util.DBConnectionPool;

// 이 리스너는 웹 애플리켕션이 시작 될 때 MemberDao 객체를 준비하여 ServletContext에 보관하는 것입니다.
@WebListener
public class ContextLoaderListener implements ServletContextListener {
	
	DBConnectionPool connPool;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		try {
			connPool.closeAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		try {
			ServletContext sc = sce.getServletContext();
			connPool = new DBConnectionPool(sc.getInitParameter("driver")
					, sc.getInitParameter("url"), 
					sc.getInitParameter("username"), 
					sc.getInitParameter("password"));
			
			
			MemberDao dao = new MemberDao();
			dao.setDbConeectionPool(connPool);			
			sc.setAttribute("memberDao", dao);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
