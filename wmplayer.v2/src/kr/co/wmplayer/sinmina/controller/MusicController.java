package kr.co.wmplayer.sinmina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MusicController {

	@RequestMapping("/musicplayer")
	public String musicplayer(){
		return "common/MusicPlayer";
	}
	
}
