package kr.co.wmplayer.sinmina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainFormController {
	
	@RequestMapping("/main")
	public String mainform(){
		return "common/MainForm";
	}
}
