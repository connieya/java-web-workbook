package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;

public class MemberListController implements Controller {
	// 예외가 발생 했을 때는 호출자인 프런트 컨트롤러에게 던지면 된다.
	  MemberDao memberDao;
	  
	  public MemberListController setMemberDao(MemberDao memberDao) {
	    this.memberDao = memberDao;
	    return this;
	  }

	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// Map 객체에서 MemberDao를 꺼낸다.
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		
		// 회원 목록 데이터를 Map 객체에 저장한다.
		model.put("members", memberDao.selectList());
		
		return "/member/MemberList.jsp";
	}

}
