package spms.controls;

import java.util.Map;

public interface Controller {
	
	// 프런트 컨트롤러가 페이지 컨트롤러에게 일을 시키기 위해 호출하는 메서드입니다.
	// 프로턴 컨트롤러가 execute()를 호출하려면 Map 객체를 매개변수로 넘겨줘야 합니다.
	String execute(Map<String, Object> model) throws Exception;

}
