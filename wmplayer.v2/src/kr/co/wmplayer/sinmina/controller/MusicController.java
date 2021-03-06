package kr.co.wmplayer.sinmina.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import kr.co.wmplayer.sinmina.dao.music.MusicDAO;
import kr.co.wmplayer.sinmina.dao.user.UserInfoDAO;
import kr.co.wmplayer.sinmina.model.dto.BpmInfoDTO;
import kr.co.wmplayer.sinmina.model.dto.music.LikeMusicDTO;
import kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
			Model model, HttpSession session) throws MalformedURLException, IOException, InterruptedException{
		
		AddressChangeModel addr = new AddressChangeModel();
		addr.setGrid(current_longitude, current_latitude, false, true, 2);

		String current_addr = addr.getAddr();
		WeatherModel wm = new WeatherModel();

		wm.setWeatherData(current_addr);

		double current_temper = wm.getWeatherData().getTemp();
		
		String weather = wm.getWeatherData().getWeather();
		double avgweather = 0;
		double avgtemper = musicdao.avgtemper(current_temper);
		double avglike =  musicdao.avgBpm((String) session.getAttribute("success"));
		
		if(weather.contains("구름")){
			avgweather = musicdao.avgWeather("구름");
		}else{
			avgweather = musicdao.avgWeather(weather);
		}
		
		double resultavg = (avgweather + avgtemper + avglike) / 3;
		double format = Math.floor(resultavg*100)/100;
		
		List<MusicInfoDTO> lists = musicdao.selectTodayList(resultavg);

		model.addAttribute("lists", lists);
		model.addAttribute("wm", wm);
		model.addAttribute("current_addr", current_addr);
		model.addAttribute("bpm",format);

	    return "ajax/receiveData";
	}

	@RequestMapping("/MusicLikeData")
	@ResponseBody
	public String MusicLikeData(String musicID, String action, HttpSession session){


		String userID = (String) session.getAttribute("success");
		double bpm = musicdao.selectBpm(musicID);
		System.out.println(bpm);
		LikeMusicDTO like = new LikeMusicDTO(musicID, userID,bpm);
		System.out.println(musicID);

		if(musicID != null){
			if(action.equals("add")){
				musicdao.addLike(like);
				return "like";
			}else if(action.equals("delete")){
				musicdao.deleteLike(like);
				return "unlike";
			}else if(action.equals("select")){
				//전체를 담아줄 객체를 설정
				//System.out.println( dao.selectLike(like));
			 	if(musicdao.selectLike(like)){
			 		return "like";
			 	}else{
			 		return "unlike";
			 	}
			}
		}else {
			System.out.println("musicId is null");
			return "fail";
		}
		return "fail";
	}


}
