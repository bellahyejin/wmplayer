package person.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import person.beans.Person;
import person.dao.PersonDAO;

@Controller
public class PersonController {
	
	@Autowired
	private PersonDAO dao;

	@RequestMapping("/form")
	public String form(){
		
		return "form";
	}
	
	@RequestMapping("/insert")
	public String insert(Person person){
		if(dao.select(person.getName()) == null){
			if(dao.insert(person)) return "result";
			else return "redirect:form";
		}
		else return "redirect:form";
	}
	
	@RequestMapping("/list")
	public String selectAll(Model model){

		model.addAttribute("list", dao.selectAll());
		return "list";
	}
	
	@RequestMapping("/editForm")
	public ModelAndView udpateForm(@RequestParam(value="name",required=false,defaultValue="무명") String name){
		Person p = dao.select(name);
		ModelAndView mav = new ModelAndView();
		mav.addObject("p",p);
		mav.setViewName("editform");
		return mav;
	}
	
	@RequestMapping("/edit")
	public String update(Person person){
		if(dao.update(person)) return "upresult";
		else return "redirect:editForm";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="name") String name){
		if(dao.delete(name)) return "delresult";
		else return "redirect:editform";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam(value="name",required=false) String name,
					   	 @RequestParam(value="age",required=false) Integer age,
					     HttpSession session){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("age", age);
		
		List<Person> list = dao.searchResult(map);
		session.setAttribute("list",list);
		
		return "redirect:searchlist";
	}
	@RequestMapping("/searchlist")
	public String searchlist(){
		
		return "list";
	}
}
