package lesson03.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet {
	// 서블릿 컨테이너가 서블릿에 대해 호출할 메서드를 정의한 것이 Servlet 인터페이스이다.
	
	ServletConfig config;

	// 서블릿 컨테이너 종료 , 웹 어플리케이션 멈출 때 , 해당 서블릿 비활성화 시킬 때 호출 
	// 서비스 수행을 위해 확보했던 자원 해제하거나 데이터를 저장하는 마무리 작업
	@Override
	public void destroy() {

		System.out.println("destory() 호출됨");
	}

	// 서블릿 설정 정보를 다루는 ServletConfig 객체 반환
	// 서블릿 이름 , 서블릿 초기 매개변수 값, 서블릿 환경정보 
	@Override
	public ServletConfig getServletConfig() {
		System.out.println("getServletConfig() 호출됨");
		return this.config;
	}

	@Override
	public String getServletInfo() {
		return "version=1.0; author=eomjinyoung;copyright=eomjinyoung 2013";
	}

	
	// 서블릿 컨테이너가 서블릿을 생성한 후 초기화 작업을 수행하기 위해 호출하는 메서드
	@Override
	public void init(ServletConfig arg0) throws ServletException {

		System.out.println("init() 호출됨");
		this.config = arg0;
	}

	// 클라이언트가 요청할 때 마다 호출되는 메서드입니다. 실질적으로 서비스 작업을 수행
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		System.out.println("service() 호출됨");
	}

}
