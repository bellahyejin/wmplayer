package kr.co.wmplayer.sinmina.controller;

import java.util.List;

import kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO;
import kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO;
import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeFormController {
	
	@Autowired
	private ShareboardDAO sharedao;
	@Autowired
	private ColumnboardDAO columndao;
	@Autowired
	private NoticeboardDAO noticedao;
	
	@RequestMapping("/homelist")
	public String homelist(Model model){
		
		List<String> title = noticedao.hometitle(1);

		List<ColumnBoardDTO> column_rank = columndao.selectHomerank();

		List<BoardUserDTO> board_rank = sharedao.selectPop();
		
		model.addAttribute("notice", title);
		model.addAttribute("column", column_rank);
		model.addAttribute("share", board_rank);
		
		return "home";
	}
	
	
}
