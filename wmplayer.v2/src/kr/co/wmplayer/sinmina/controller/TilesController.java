package kr.co.wmplayer.sinmina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TilesController {
	
	@RequestMapping("/content")
	public String contentform(){
		return "tiles.main";
	}
	
	@RequestMapping("/home")
	public String homeform(){
		return "home";
	}
	
	@RequestMapping("/noticelist")
	public String noticelist(){
		return "noticelist";
	}
	
	@RequestMapping("/sharelist")
	public String sharelist(){
		return "sharelist";
	}
	
	@RequestMapping("/columnlist")
	public String columnlist(){
		return "columnlist";
	}
	
	@RequestMapping("/userinfo")
	public String userinfo(){
		return "userinfo";
	}
	
	@RequestMapping("/drop")
	public String userdrop(){
		return "drop";
	}
	
	@RequestMapping("/update")
	public String userupdate(){
		return "update";
	}
	
	@RequestMapping("/noticewrite")
	public String noticewrite(){
		return "noticewrite";
	}
	
	@RequestMapping("/noticedetail")
	public String noticedetail(){
		return "noticedetail";
	}
	
	@RequestMapping("/columnform")
	public String columnform(){
		return "columnform";
	}
	
	@RequestMapping("/columndetail")
	public String columndetail(){
		return "columndetail";
	}
	
	@RequestMapping("/sharewrite")
	public String sharewrite(){
		return "sharewrite";
	}
	
	@RequestMapping("/sharedetail")
	public String sharedetial(){
		return "sharedetail";
	}
	
	@RequestMapping("/manager")
	public String manager(){
		return "manager";
	}
	
	@RequestMapping("/managerChartLogin")
	public String managerChartLogin(){
		return "managerChartLogin";
	}
	
	@RequestMapping("/managerChartJoin")
	public String managerChartJoin(){
		return "managerChartJoin";
	}
	
	@RequestMapping("/dropReason")
	public String dropReason(){
		return "dropReason";
	}
}
