package kr.co.wmplayer.sinmina.controller;

import javax.servlet.http.HttpSession;

import kr.co.wmplayer.sinmina.dao.user.UserInfoDAO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MusicController {
	
	@Autowired
	private UserInfoDAO dao = new UserInfoDAO();
	
	@RequestMapping("/musicplayer")
	public ModelAndView musicplayer(HttpSession session){
		String userid = (String) session.getAttribute("success");
		UserInfoDTO user = dao.select(userid);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user",user);
		mav.setViewName("common/MusicPlayer");
		
		return mav;
	}
	
	
}
