package kr.co.wmplayer.sinmina.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import kr.co.wmplayer.sinmina.dao.music.MusicDAO;
import kr.co.wmplayer.sinmina.dao.user.UserInfoDAO;
import kr.co.wmplayer.sinmina.model.dto.music.LikeMusicDTO;
import kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Weather.AddressChangeModel;
import Weather.WeatherModel;

@Controller
public class MusicController {
	
	@Autowired
	private UserInfoDAO dao;
	
	@Autowired
	private MusicDAO musicdao;
	
	@RequestMapping("/musicplayer")
	public ModelAndView musicplayer(HttpSession session){
		String userid = (String) session.getAttribute("success");
		UserInfoDTO user = dao.select(userid);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("user",user);
		mav.setViewName("common/MusicPlayer");
		
		return mav;
	}
	
	@RequestMapping("/ajax/receiveData")
	public String receiveData(@RequestParam(value="cur_Latitude") double current_latitude,
			@RequestParam(value="cur_Longitude") double current_longitude ,
			Model model) throws MalformedURLException, IOException, InterruptedException{
		AddressChangeModel addr = new AddressChangeModel();
		addr.setGrid(current_longitude, current_latitude, false, true, 2);
		
		String current_addr = addr.getAddr();
		WeatherModel wm = new WeatherModel();
		
		wm.setWeatherData(current_addr);
		
		double current_temper = wm.getWeatherData().getTemp();
		List<MusicInfoDTO> lists = musicdao.selectTodayList(current_temper);
		model.addAttribute("lists", lists);
		model.addAttribute("wm", wm);
		model.addAttribute("current_addr", current_addr);
		
	    return "ajax/receiveData";
	}
	
	@RequestMapping("/ajax/MusicLikeData")
	public String MusicLikeData(@RequestParam(value="musicID") String musicID,
								@RequestParam(value="status") String status,
								HttpSession session){
		
		System.out.println("::: MusicID, "+musicID+"status:::::"+status);
		
		String userID = (String) session.getAttribute("success");
		LikeMusicDTO like = new LikeMusicDTO(musicID, userID);
		String msg = "";
		
		if(musicID != null){
			if(status.equals("add")){
				musicdao.addLike(like);
				System.out.println("like add : success");
			}else if(status.equals("delete")){
				musicdao.deleteLike(like);
				System.out.println("like delete add: success");
			}else if(status.equals("select")){
				//전체를 담아줄 객체를 설정
				//System.out.println( dao.selectLike(like));
			 	if(musicdao.selectLike(like)){
			 		msg = "like";
			 	}else{
			 		msg = "unlike";
			 	}
			}
		}else {
			System.out.println("musicId is null");
		}
		return msg;
	}
}
