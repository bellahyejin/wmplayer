package kr.co.wmplayer.sinmina.controller;

import java.util.List;

import kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeBoardController {
	
	@Autowired
	NoticeboardDAO dao;
	
	@RequestMapping("/noticelist")
	public String noticelist(){
		return "noticelist";
	}
	
	@RequestMapping("/notice/list")
	public ModelAndView listform(@RequestParam(value="i", defaultValue="0") int i ){
		
		ModelAndView mav = new ModelAndView();
		
		int totalNumber = dao.dataSize();
		int page_max = 10;
		int endPage = totalNumber / page_max;
		int beginPage = 0;
		
		mav.addObject("totalNumber", totalNumber);
		mav.addObject("page_max", page_max);
		mav.addObject("endPage", endPage);
		mav.addObject("beginPage", beginPage);

		if (i == 0 ) 
		{
			List<NoticeBoardDTO> list = dao.selectAll((page_max) * i,page_max);
			mav.addObject("noticelist", list);
		} 
		else 
		{
			List<NoticeBoardDTO> list = dao.selectAll((page_max) * i,page_max);
			mav.addObject("noticelist", list);
		}
			
			mav.setViewName("noticelist");
		return mav;
	}
	
	@RequestMapping("/notice/detail")
	public ModelAndView detailform(@RequestParam(value="notice_seq") int notice_seq){
		
		ModelAndView mav = new ModelAndView();
		
		NoticeBoardDTO notice = dao.select(notice_seq);
		List<String> seqList = dao.selectSeq();
		
		int size = seqList.size();
		
		int nowIndex = seqList.indexOf(""+notice_seq);
		int nextsu = 0;
		int beforesu = 0;
		
		if (nowIndex == 0) {
			nextsu = Integer.parseInt(seqList.get(0));
			beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
		} else if (nowIndex == size - 1) {
			nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
			beforesu = Integer.parseInt(seqList.get(size - 1));

		} else {
			nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
			System.out.println("다음 인덱스" + nextsu);
			beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
			System.out.println("이전 인덱스" + beforesu);
		}
		
		dao.updateView(notice_seq);
		
		mav.addObject("notice", notice);
		mav.addObject("nextsu", nextsu);
		mav.addObject("beforesu", beforesu);
		
		mav.setViewName("noticedetail");
		
		return null;
	}
	
}
