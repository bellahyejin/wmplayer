package kr.co.wmplayer.sinmina.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.co.wmplayer.sinmina.dao.manager.ManagerDAO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JSONOperate;
import util.ListAndMapOperate;
import util.ListMap;

@Controller
public class ManagerController
{
	@Autowired
	ManagerDAO managerDAO;

	JSONOperate jsonOperate = JSONOperate.getInstance();
	ListAndMapOperate lamOperate = ListAndMapOperate.getInstance();
	Map<String, String> column_name_y;

	int userinfo_max_element = 15, dropreason_max_element = 5, page_div = 4, pres_page = 1;

	@RequestMapping(value = "/manager/temp", method = RequestMethod.GET)
	public String temp(Model model, HttpServletRequest request)
	{
		return main(model, request);
	}

	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	public String main(Model model, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		Object userid = session.getAttribute("success");

		column_name_y = new ListMap<String, String>();
		column_name_y.put("column_data", "label");
		column_name_y.put("cnt", "y");

		if ("admin".equals(userid))
		{
			String action = request.getParameter("action");
			System.out.println(action);
			if (action == null || action.equals("userinfo")) return userInfoTable(model, request);
			else if (action.equals("dropreason")) return userDropReasonTable(model, request);
			else if (action.equals("chartlogin")) return chartLogin(model, request);
			else if (action.equals("chartjoin")) return chartJoin(model, request);
			else return null;
		}
		else return "redirect:../intro";
	}

	public String userInfoTable(Model model, HttpServletRequest request)
	{
		pres_page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		request.setAttribute("pres_page", pres_page);

		return "manager/ManagerView";
	}

	public String userDropReasonTable(Model model, HttpServletRequest request)
	{
		return "manager/DropUserReason";
	}

	public String chartLogin(Model model, HttpServletRequest request)
	{
		return "manager/ManagerChartViewLogin";
	}

	public String chartJoin(Model model, HttpServletRequest request)
	{
		return "manager/ManagerChartViewJoin";
	}

	@RequestMapping(value = "/manager/userinfo.ajax", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public @ResponseBody String userInfo(HttpServletRequest request)
	{
		StringBuffer sb = new StringBuffer(), sb2 = new StringBuffer();
		String status = request.getParameter("status");
		String gender = request.getParameter("gender");
		String value = request.getParameter("value") == null || request.getParameter("value").equals("") ? null : request.getParameter("value");
		List<String> status_list = new ArrayList<String>();
		if (status.equals("all") || status.equals("block")) status_list.add("b");
		if (status.equals("all") || status.equals("current")) status_list.add("c");
		List<String> gender_list = new ArrayList<String>();
		if (gender.equals("all") || gender.equals("man")) gender_list.add("남");
		if (gender.equals("all") || gender.equals("woman")) gender_list.add("여");

		ListMap<String, Object> map = new ListMap<String, Object>();
		map.put("select_column", "userid, name, birth, gender, email, status");
		map.put("status", status_list);
		map.put("gender", gender_list);
		map.put("compare_column", request.getParameter("search"));
		map.put("value", value);

		int member_num = managerDAO.membercount(map);
		int pres_page = Integer.parseInt(request.getParameter("page"));
		int max_page = ((int) member_num / userinfo_max_element) + (member_num % userinfo_max_element == 0 ? 0 : 1);
		int begin_page = (pres_page <= 1 + page_div || 1 + page_div * 2 >= max_page ? 1 : (pres_page + page_div >= max_page ? max_page - page_div * 2 : pres_page - page_div));
		int end_page = (pres_page >= max_page - page_div || 1 >= max_page - page_div * 2 ? max_page : (pres_page <= 1 + page_div ? 1 + page_div * 2 : pres_page + page_div));
		int rownum_start_idx = userinfo_max_element * (pres_page - 1), count = 0;
		boolean flag;

		List<UserInfoDTO> list = managerDAO.selectmemberinfo(map, rownum_start_idx, userinfo_max_element);

		sb = sb.append("<tr class=\"manager_title\">")
				.append("<th width=\"8%\">번호</th>")
				.append("<th width=\"12%\">아이디</th>")
				.append("<th width=\"8%\">이 름</th>")
				.append("<th width=\"10%\">생년월일</th>")
				.append("<th width=\"4%\">성별</th>")
				.append("<th width=\"28%\">이메일</th>")
				.append("<th width=\"10%\"></th>")
				.append("</tr>");

		if (list.size() > 0)
		{
			for (UserInfoDTO data : list)
			{
				status = data.getStatus();

				sb = sb.append("<tr align=\"center\">")
						.append("<td>".concat(Integer.toString((member_num - (rownum_start_idx + count++)))).concat("</td>"))
						.append("<td>".concat(data.getUserID()).concat("</td>"))
						.append("<td>".concat(data.getName()).concat("</td>"))
						.append("<td>".concat(data.getBirth()).concat("</td>"))
						.append("<td>".concat(data.getGender()).concat("</td>"))
						.append("<td>".concat(data.getEmail()).concat("</td>"))
						.append("<td>")
						.append("<input type=\"button\" class=\"button\" name=".concat(status.equals("b") ? "current" : "block").concat("-").concat(data.getUserID()).concat(" style=\"font: 9pt\" value=").concat(status.equals("b") ? "활성" : "차단") + ">")
						.append("<input type=\"button\" class=\"button\" name=\"drop-".concat(data.getUserID()).concat("\" style=\"font: 9pt\" value=\"탈퇴\">"))
						.append("</tr>");
			}

			sb2 = sb2.append("<ul class=\"page-list\">")
					.append("<div id=\"first\">")
					.append("<li class=\"").append((flag = pres_page == 1) ? "none_a" : "page\" id=\"first-page\"><a href=\"?page=1").append("\">start<").append(flag ? "" : "/a><").append("/li>")
					.append("</div>")
					.append("<div id=\"middle\">");

			for (int i = begin_page; i <= end_page; i++) sb2 = sb2.append("<li class=\"").append((flag = pres_page == i) ? "none_a" : "page\"><a href=\"?page=").append(i).append("\">").append(i).append("<").append(flag ? "" : "/a><").append("/li>");

			sb2 = sb2.append("</div>")
						.append("<div id=\"end\">")
						.append("<li class=\"").append((flag = pres_page == max_page) ? "none_a" : "page\" id=\"end-page\"><a href=\"?page=").append(max_page).append("\">end<").append(flag ? "" : "/a><").append("/li>")
						.append("</div>")
						.append("</ul>");
		}
		else
		{
			sb = sb.append("<tr align=\"center\">")
					.append("<td colspan=\"7\">검색 결과가 없습니다.</td>");
		}

		return sb.toString().concat("|").concat(sb2.toString());
	}

	@RequestMapping(value = "/manager/dropreason.ajax", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public void dropReason(HttpServletRequest request)
	{
		//
	}
}
