package kr.co.wmplayer.sinmina.controller;

import java.util.List;

import kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO;
import kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO;
import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO;
import kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TilesController {

	@Autowired
	private NoticeboardDAO noticedao;
	@Autowired
	private ColumnboardDAO columndao;
	@Autowired
	private ShareboardDAO sharedao;

	@RequestMapping("/content")
	public String contentform( Model model) {

		List<NoticeBoardDTO> title = noticedao.selectAll(0, 5);

		List<ColumnBoardDTO> column_rank = columndao.selectHomerank();

		List<BoardUserDTO> board_rank = sharedao.selectPop();

		model.addAttribute("notice", title);
		model.addAttribute("column", column_rank);
		model.addAttribute("share", board_rank);

		return "tiles.main";
	}
}
