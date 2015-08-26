package kr.co.wmplayer.sinmina.controller;

import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShareBoardController {

	@Autowired
	private ShareboardDAO dao;
	
	@RequestMapping("/sharelist")
	public String sharelist(){
		return "sharelist";
	}
	
	
}
