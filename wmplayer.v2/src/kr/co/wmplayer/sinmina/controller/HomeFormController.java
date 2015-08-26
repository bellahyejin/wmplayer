package kr.co.wmplayer.sinmina.controller;

import java.util.List;

import kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO;
import kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO;
import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeFormController {
	
	@Autowired
	private ShareboardDAO sharedao;
	@Autowired
	private ColumnboardDAO columndao;
	@Autowired
	private NoticeboardDAO noticedao;
	
	@RequestMapping("/home")
	public String homeform(){
		return "home";
	}
	
	@RequestMapping("/homelist")
	public ModelAndView homelist(){
		
		ModelAndView mav = new ModelAndView();
		
		List<String> title = noticedao.hometitle(1);

		List<ColumnBoardDTO> column_rank = columndao.selectHomerank();

		List<BoardUserDTO> board_rank = sharedao.selectPop();
		
		mav.addObject("notice", title);
		mav.addObject("column", column_rank);
		mav.addObject("share", board_rank);
		mav.setViewName("home");
		
		return mav;
	}
	
	
}
