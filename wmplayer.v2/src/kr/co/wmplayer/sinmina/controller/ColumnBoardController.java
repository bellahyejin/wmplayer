package kr.co.wmplayer.sinmina.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO;
import kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ColumnBoardController {
	
	@Autowired
	ColumnboardDAO columndao ;
	
	@RequestMapping("/columnform")
	public String columnform(){
		return "columnform";
	}
	
	@RequestMapping("/columnadd")
	public String columnadd(@RequestParam(value="title") String title,
							@RequestParam(value="mood") String mood,
							@RequestParam(value="style") String style,
							@RequestParam(value="contents") String contents,
							Model model){
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("title", title);
		map.put("mood", mood);
		map.put("style", style);
		map.put("contents", contents);
		
		if(title!=null && style!=null && contents != null && !(mood.contains("선택"))){
			if(columndao.columnInsert(map)){
				return "redirect:column";
			}else{
				model.addAttribute("alertMsg", "<script>alert('작성에 실패 하였습니다. 다시 입력해주세요')</script>");
			}
		}else {
			model.addAttribute("alertMsg", "<script>alert('내용을 입력해주세요')</script>");
		}
		return "columnform";
	}
	
	
	@RequestMapping("/column")
	public String columnlist(@RequestParam(value="i", defaultValue="1") int i, Model model){
		
		int totalNumber =columndao.dataSize(); 
		int page_max = 10;
		int endPage = totalNumber / page_max;
		
		model.addAttribute("endPage", endPage);
		
		List<ColumnBoardDTO> list = columndao.columnSelectAll((page_max) * (i-1));	
		for(int idx = 0 ; idx < list.size(); idx++){
			ColumnBoardDTO board = list.get(idx);
			String date = board.getUpdate_day().substring(0, 10).replace("-", "/");
			board.setUpdate_day(date);
		}
		model.addAttribute("column", list);
		
		return "columnlist";
	}
	
	@RequestMapping("/columndetail")
	public String columndetail(){
		
		
		
		return "columndetail";
	}
}
