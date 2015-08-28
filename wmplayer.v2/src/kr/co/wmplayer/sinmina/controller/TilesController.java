package kr.co.wmplayer.sinmina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TilesController {
	
	@RequestMapping("/content")
	public String contentform(){
		return "tiles.main";
	}
}
