package kr.co.wmplayer.sinmina.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.co.wmplayer.sinmina.dao.user.UserInfoDAO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private UserInfoDAO dao;

	@RequestMapping("/intro")
	public String intro(){
		return "common/IntroForm";
	}

	@RequestMapping("/login")
	public String login(@RequestParam(value="userID") String userID,
						@RequestParam(value="passwd") String passwd, HttpSession session){

		if(userID != null && passwd != null)
		{
			UserInfoDTO user = new UserInfoDTO(userID, passwd);

			if (dao.loginproccess(user) != 0) {
				session.setAttribute("success", userID);
				dao.loginLogInsert(userID);
				return "common/MainForm";
			} else {
				return "redirect:intro";
			}
		}
		else
			return "redirect:intro";
	}

	@RequestMapping("/findId" )
	@ResponseBody
	public String findid(UserInfoDTO user, HttpServletRequest requset){
		String result = null;
		if(user.getName() == null || user.getEmail() == null) {
			return 	"해당 정보를 입력해주세요"  //정보를 입력하지 않았을 경우
					+ "<input class='input-submit' type='submit' id='submit-id' value='아이디 찾기' />";
		}
		else{
			String userid = dao.findid(user);
			if(userid != null){
				result = "고객님의 아이디는 <span id='userid'>"+userid+"</span> 입니다"
						+ "<input class='input-submit' type='button' id='submit-back' value='뒤로가기' onclick='moveback()'/>";		
				return result;
			}
			else //회원 정보가 없을 경우
				return "등록된 회원정보가 없습니다. 다시입력해주세요"
						+ "<input class='input-submit' type='submit' id='submit-id' value='아이디 찾기' />";
		}
	}

	@RequestMapping("/findPass")
	@ResponseBody
	public String findpass(UserInfoDTO user){

		String result;
		if(user.getUserID() != null && user.getName() != null && user.getEmail() != null){ //파라미터가 있을경우
			String userpass = dao.findpass(user); //아이디, 이름, 이메일 검사
			if(userpass != null){//정보가 맞다면
				String responsepass = userpass.substring(0, 3);
				
				for(int i = 0 ; i < userpass.length() - 3; i++){
					responsepass += "*"; //전체 비밀번호의 앞에 세자리만 보여준다 
				}
				
				result ="고객님의 아이디는 <span id='userid'>"+responsepass+"</span> 입니다"
						+ "<input class='input-submit' type='button' id='submit-back' value='뒤로가기' onclick='moveback()'/>";	;
				return result;
			}
			else //해당정보가 없을 경우
				return "등록된 회원정보가 없습니다. 다시입력해주세요"
						+ "<input class='input-submit' type='submit' id='submit-pass' value='비밀번호찾기' />";
		}else //정보를 입력하지 않았을 경우 
			return "해당 정보를 입력해주세요"
					+ "<input class='input-submit' type='submit' id='submit-pass' value='비밀번호찾기' />";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		session.invalidate();
		return "redirect:intro";
	}
}