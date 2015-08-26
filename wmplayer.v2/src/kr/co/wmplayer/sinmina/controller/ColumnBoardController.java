package kr.co.wmplayer.sinmina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ColumnBoardController {
	
	@RequestMapping("/columnlist")
	public String columnlist(){
		return "columnlist";
	}
	
}
