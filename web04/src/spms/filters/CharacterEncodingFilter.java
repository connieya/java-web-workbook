package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;


// web.xml에 작성하는 대ㅐ신 소스파일에 직접 배치 정보를 설정 할 수 있다.
//@WebFilter(
//		urlPatterns = "/*",
//		initParams = {
//				@WebInitParam(name = "encoding" , value = "UTF-8")
//		})
public class CharacterEncodingFilter implements Filter {
	FilterConfig config;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding(config.getInitParameter("encoding"));
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
			this.config = config;
	}

}
