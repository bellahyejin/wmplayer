package kr.co.wmplayer.sinmina.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import kr.co.wmplayer.sinmina.dao.music.MusicDAO;
import kr.co.wmplayer.sinmina.dao.user.UserInfoDAO;
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
	private MusicDAO mDao;
	
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
//		double current_latitude = Double.valueOf(request.getParameter("cur_Latitude")).doubleValue();
//		double current_longitude = Double.valueOf(request.getParameter("cur_Longitude")).doubleValue();
//		
//		
//		MusicDAO dao = new MusicDAO();
		
		AddressChangeModel addr = new AddressChangeModel();
		addr.setGrid(current_longitude, current_latitude, false, true, 2);
		
		String current_addr = addr.getAddr();
		WeatherModel wm = new WeatherModel();
		
		wm.setWeatherData(current_addr);
		
		double current_temper = wm.getWeatherData().getTemp();
//		String current_weather = wm.getWeatherData().getWeather();
		List<MusicInfoDTO> lists = mDao.selectTodayList(current_temper);
		model.addAttribute("lists", lists);
		model.addAttribute("wm", wm);
		model.addAttribute("current_addr", current_addr);
		
		System.out.println("addr : "+ current_addr+", temper:" +current_temper+", lists: "+lists.get(1).getTitle());
		
		
	    return "receiveData";
	}
	
	
}
