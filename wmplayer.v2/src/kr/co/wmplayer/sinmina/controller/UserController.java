package kr.co.wmplayer.sinmina.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;
import kr.co.wmplayer.sinmina.dao.music.MusicDAO;
import kr.co.wmplayer.sinmina.dao.user.UserInfoDAO;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import kr.co.wmplayer.sinmina.model.dto.music.LikeMusicDTO;
import kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController
{

	@Autowired
	private UserInfoDAO dao;
	@Autowired
	private ShareboardDAO sharedao;
	@Autowired
	private MusicDAO musicdao;

	@RequestMapping("/intro")
	public String intro(HttpSession session)
	{
		return session.getAttribute("success") == null ? "common/IntroForm" : "redirect:main";
	}

	@RequestMapping("/login")
	public String login(UserInfoDTO bean, HttpSession session)
	{
		if (bean.getUserID() != null && bean.getPasswd() != null)
		{
			if (dao.loginproccess(bean) != 0)
			{
				String id = bean.getUserID();

				session.setAttribute("success", id);
				dao.loginLogInsert(id);
				return "redirect:main";
			}
		}
		return "redirect:intro";
	}

	@RequestMapping(value = "/main")
	public String main()
	{
		return "common/MainForm";
	}

	@ResponseBody @RequestMapping("/findId")
	public String findid(UserInfoDTO user, HttpServletRequest requset)
	{
		String result = null;
		if (user.getName() == null || user.getEmail() == null)
		{
			return "해당 정보를 입력해주세요" // 정보를 입력하지 않았을 경우
					+ "<input class='input-submit' type='button' id='submit-id' value='아이디 찾기' />";
		}
		else
		{
			String userid = dao.findid(user);
			if (userid != null)
			{
				result = "고객님의 아이디는 <span id='userid'>" + userid + "</span> 입니다" + "<input class='input-submit' type='button' id='submit-back' value='뒤로가기' onclick='moveback()'/>";
				return result;
			}
			else // 회원 정보가 없을 경우
			return "등록된 회원정보가 없습니다. 다시입력해주세요" + "<input class='input-submit' type='button' id='submit-id' value='아이디 찾기' />";
		}
	}

	@ResponseBody @RequestMapping("/findPass")
	public String findpass(UserInfoDTO user)
	{

		String result;
		if (user.getUserID() != null && user.getName() != null && user.getEmail() != null)
		{ // 파라미터가 있을경우
			String userpass = dao.findpass(user); // 아이디, 이름, 이메일 검사
			if (userpass != null)
			{// 정보가 맞다면
				String responsepass = userpass.substring(0, 3);

				for (int i = 0; i < userpass.length() - 3; i++)
				{
					responsepass += "*"; // 전체 비밀번호의 앞에 세자리만 보여준다
				}

				result = "고객님의 패스워드는 <span id='userid'>" + responsepass + "</span> 입니다" + "<input class='input-submit' type='button' id='submit-back' value='뒤로가기' onclick='moveback()'/>";
				;
				return result;
			}
			else // 해당정보가 없을 경우
			return "등록된 회원정보가 없습니다. 다시입력해주세요" + "<input class='input-submit' type='button' id='submit-pass' value='비밀번호찾기' />";
		}
		else // 정보를 입력하지 않았을 경우
		return "해당 정보를 입력해주세요" + "<input class='input-submit' type='button' id='submit-pass' value='비밀번호찾기' />";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session)
	{

		session.invalidate();
		return "redirect:intro";
	}

	@RequestMapping("/userinfo")
	public String userinfoform(HttpSession session, Model model)
	{
		String pattern = "#####.##";
		String userid = (String) session.getAttribute("success");
		UserInfoDTO bean = dao.select(userid);
		model.addAttribute("user", bean);
		List<BoardUserDTO> list2 = sharedao.selectMyboard(userid);
		List<LikeMusicDTO> list = musicdao.selectLikeMusic(userid);

		DecimalFormat format = new DecimalFormat(pattern);

		String avgbpm = format.format(musicdao.avgBpm(userid));

		List<MusicInfoDTO> musiclist = new ArrayList<MusicInfoDTO>();

		for (int i = 0; i < list.size(); i++)
		{
			MusicInfoDTO music = musicdao.likemusic(list.get(i).getMusicID());
			if (music != null)
			{
				musiclist.add(music);
			}
		}
		model.addAttribute("share", list2);
		model.addAttribute("music", musiclist);
		model.addAttribute("avgbpm", avgbpm);
		model.addAttribute("listsize", list2.size());
		model.addAttribute("musicsize", musiclist.size());

		return "userinfo";
	}

	@ResponseBody @RequestMapping("/update")
	public String userupdate(String passwd, String name, String year, String month, Date birth, String email, HttpSession session, Model model)
	{
		String userid = (String) session.getAttribute("success");
		String birth_dt = new SimpleDateFormat("yyyy/MM/dd").format(birth);

		UserInfoDTO user = new UserInfoDTO(passwd, name, birth_dt, email);
		user.setUserID(userid);
		if (dao.update(user)) { return "userinfo"; }

		return "userinfo";
	}

	@RequestMapping("/join")
	public String userjoin(@RequestParam(value = "userID", required = false) String userID, @RequestParam(value = "passwd", required = false) String passwd, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "year", required = false) String year, @RequestParam(value = "month", required = false) String month, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "gender", required = false) String gender, @RequestParam(value = "email_id", required = false) String email_id, @RequestParam(value = "email_addr", required = false) String addr, Model model)
	{
		if (userID != null && passwd != null && gender != null && email_id != null && year != null && month != null && date != null)
		{
			String birth = year + "/" + month + "/" + date;
			String email = email_id + "@" + addr;

			UserInfoDTO user = new UserInfoDTO(userID, passwd, name, birth, gender, email);
			if (dao.infoInsert(user))
			{
				return "redirect:intro";
			}
			else
			{
				model.addAttribute("alertMsg", "<script>alert('가입에 실패 하였습니다. 다시 시도 해주세요')</script>");
				return "redirect:intro";
			}
		}
		else
		{
			model.addAttribute("alertMsg", "<script>alert('회원 정보를 입력해주세요')</script>");
			return "redirect:intro";
		}
	}

	// 유효성 검사 중복확인과 pass워드
	@ResponseBody @RequestMapping("/duplicationid")
	public String duplicationid(String userID)
	{
		System.out.println("userID: " + userID);
		int result = dao.selectcheck(userID);
		if (result == 1) return "unable";
		else return "able";
	}

	// drop
	@RequestMapping("/dropform")
	public String dropform()
	{
		return "drop";
	}

	@RequestMapping("/userdrop")
	public String userdrop(@RequestParam(value = "dropreason", required = false) Integer dropreason, @RequestParam(value = "etctext") String etctext, HttpSession session)
	{
		String user = (String) session.getAttribute("success");
		session.invalidate();
		dao.dropupdate(dropreason);
		if (dropreason == 5) dao.dropReason(etctext);
		dao.delete(user);

		return "redirect:intro";
	}
}
