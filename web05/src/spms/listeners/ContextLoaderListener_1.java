package spms.listeners;

// Apache DBCP 적용
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import spms.dao.MemberDao;
import spms.dao.MemberDao2;

public class ContextLoaderListener_1 implements ServletContextListener {
	// 아파치 DBCP 라이브러리에서 DataSource 인터페이스를 구현한 클래스
	BasicDataSource ds; 
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		try {
			ServletContext sc = sce.getServletContext();
			
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));
			
			MemberDao2 memberDao = new MemberDao2();
			memberDao.setDataSource(ds);
			
			sc.setAttribute("memberDao", memberDao);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	// 공용 자원을 해제하는 코드가 놓이는 최적의 장소이다.
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			if(ds != null) ds.close();
		} catch (SQLException e) {
			
		}
		
	}

}
