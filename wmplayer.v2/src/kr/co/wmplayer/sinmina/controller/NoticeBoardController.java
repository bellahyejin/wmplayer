package kr.co.wmplayer.sinmina.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeBoardController {
	
	@Autowired
	NoticeboardDAO dao;
	
	@RequestMapping("/noticeform")
	public String noticeinput(){
		return "noticewrite";
	}
	
	@RequestMapping("/noticeadd")
	public String noticeadd(@RequestParam(value="title") String title,
							@RequestParam(value="contents") String contents,
							Model model, HttpServletRequest req){
		try {
			req.setCharacterEncoding("EUC-KR");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (title != null && contents != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			map.put("contents", contents);

			dao.insert(map);
			return "noticelist";
		}else{
			model.addAttribute("msg","<script>alert('제목이나 내용을 입력해주세요')</script>");
			return "noticewrite";
		}
	}
	
	@RequestMapping("/notice")
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
	
	@RequestMapping("/noticedetail")
	public ModelAndView detailform(@RequestParam(value="notice_seq") int notice_seq){
		
		ModelAndView mav = new ModelAndView();
		
		System.out.println(notice_seq);
		
		NoticeBoardDTO notice = dao.select(notice_seq);
		List<String> seqList = dao.selectSeq();
		String alertMsg = null;
		int size = seqList.size();
		
		int nowIndex = seqList.indexOf(""+notice_seq);
		int nextsu = 0;
		int beforesu = 0;
		
		if (nowIndex == 0) {
			nextsu = Integer.parseInt(seqList.get(0));
			beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
			alertMsg = "<script>function warning(){alert('다음페이지가 없습니다');}</script>";
		} else if (nowIndex == size - 1) {
			nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
			beforesu = Integer.parseInt(seqList.get(size - 1));
			alertMsg = "<script>function warning(){alert('이전페이지가 없습니다');}</script>";
		} else {
			nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
			System.out.println("다음 인덱스" + nextsu);
			beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
			System.out.println("이전 인덱스" + beforesu);
		}
		
		dao.updateView(notice_seq);
		
		mav.addObject("noticedetail", notice);
		mav.addObject("nextsu", nextsu);
		mav.addObject("beforesu", beforesu);
		mav.addObject("alertMsg", alertMsg);
		
		mav.setViewName("noticedetail");
		
		return mav;
	}
	
}
