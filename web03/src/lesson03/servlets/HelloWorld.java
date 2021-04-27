package lesson03.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet {
	// ���� �����̳ʰ� ������ ���� ȣ���� �޼��带 ������ ���� Servlet �������̽��̴�.
	
	ServletConfig config;

	// ���� �����̳� ���� , �� ���ø����̼� ���� �� , �ش� ���� ��Ȱ��ȭ ��ų �� ȣ�� 
	// ���� ������ ���� Ȯ���ߴ� �ڿ� �����ϰų� �����͸� �����ϴ� ������ �۾�
	@Override
	public void destroy() {

		System.out.println("destory() ȣ���");
	}

	// ���� ���� ������ �ٷ�� ServletConfig ��ü ��ȯ
	// ���� �̸� , ���� �ʱ� �Ű����� ��, ���� ȯ������ 
	@Override
	public ServletConfig getServletConfig() {
		System.out.println("getServletConfig() ȣ���");
		return this.config;
	}

	@Override
	public String getServletInfo() {
		return "version=1.0; author=eomjinyoung;copyright=eomjinyoung 2013";
	}

	
	// ���� �����̳ʰ� ������ ������ �� �ʱ�ȭ �۾��� �����ϱ� ���� ȣ���ϴ� �޼���
	@Override
	public void init(ServletConfig arg0) throws ServletException {

		System.out.println("init() ȣ���");
		this.config = arg0;
	}

	// Ŭ���̾�Ʈ�� ��û�� �� ���� ȣ��Ǵ� �޼����Դϴ�. ���������� ���� �۾��� ����
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		System.out.println("service() ȣ���");
	}

}
