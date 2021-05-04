package spms.listeners;


// 서버에서 제공하는 DataSource 사용하기
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import spms.dao.MemberDao2;

public class ContextLoaderListener_2 implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		try {
			ServletContext sc = sce.getServletContext();
			// 톰캣 서버에서 자원을 찾기 위해 InitialContext 객체 생성
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/studydb");
			
			
			MemberDao2 memberDao = new MemberDao2();
			memberDao.setDataSource(ds);
			
			sc.setAttribute("memberDao", memberDao);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	// 톰켓  서버에 자동으로 해제하라고 설정되어 있기 대문에 메서드 안에 아무것도 작성하지 않았다.
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}

}
