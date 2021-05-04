package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// Map 객체에 "Member" 객체가 없기 때문에 get 요청
		if(model.get("member")==null) { // 입력 폼을 요청할 때
			return "/member/MemberForm.jsp";
		
			// Map 객체에 VO 객체 "Member"가 들어 있으면 POST 요청
		}else { // 회원 등록을 요청 할 때 
			MemberDao memberDao = (MemberDao) model.get("memberDao");
			
			Member member = (Member) model.get("member");
			memberDao.insert(member);
			return "redirect::list.do";
		}
		
	}

}
