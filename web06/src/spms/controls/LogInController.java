package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

public class LogInController implements Controller {
	MemberDao memberDao;
	
	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("loginInfo") == null) { // �엯�젰 �뤌�쓣 �슂泥��븷�븣
			return "/auth/LogInForm.jsp";
		}else { // �쉶�썝 �벑濡앹쓣 �슂泥��븷 �븣
			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member loginInfo = (Member)model.get("loginInfo");
			
			Member member = memberDao.exist(
					loginInfo.getEmail(), loginInfo.getPassword());
			
			if(member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			}else {
				return "/auth/LogInFail.jsp";
			}
					
		}
	}

}
