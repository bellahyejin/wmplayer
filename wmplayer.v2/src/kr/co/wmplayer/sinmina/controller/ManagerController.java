package kr.co.wmplayer.sinmina.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import kr.co.wmplayer.sinmina.dao.manager.ManagerDAO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JSONOperate;
import util.ListAndMapOperate;
import util.ListMap;

@Controller
public class ManagerController
{
	@Autowired ManagerDAO managerDAO;

	JSONOperate jsonOperate = JSONOperate.getInstance();
	ListAndMapOperate lamOperate = ListAndMapOperate.getInstance();
	Map<String, Object> column_name_y, map;

	int max_element = 15, page_div = 4, pres_page = 1;
	String compare_data, output_data;

	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	public String main(Model model, HttpServletRequest request)
	{
		Object userid = request.getSession().getAttribute("success");

		column_name_y = new ListMap<String, Object>();
		column_name_y.put("column_data", "label");
		column_name_y.put("cnt", "y");

		if (userid != null)
		{
			if (userid.equals("admin"))
			{
				map = new ListMap<String, Object>();
				String action = request.getParameter("action");

				if (action == null || action.equals("userinfo")) return userInfoTable(model, request);
				else if (action.equals("dropreason")) return userDropReasonTable(model, request);
				else if (action.equals("chartlogin")) return chartLogin(model, request);
				else if (action.equals("chartjoin")) return chartJoin(model, request);
				else return null;
			}
			else return "redirect:/main";
		}
		else return "redirect:/intro";
	}

	public String userInfoTable(Model model, HttpServletRequest request)
	{
		pres_page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		request.setAttribute("pres_page", pres_page);
		return "manager";
	}

	public String userDropReasonTable(Model model, HttpServletRequest request)
	{
		pres_page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		map.clear();
		map.put("drop_seq", 5);
		request.setAttribute("drop_reason_list", managerDAO.reasonselect(map));
		map.clear();
		map.put("drop_seq", 6);
		request.setAttribute("drop_etc_list", managerDAO.reasonselect(map));
		return "dropReason";
	}

	public String chartLogin(Model model, HttpServletRequest request)
	{
		map.clear();
		map.put("count_column", "id");
		map.put("table", "loginlog");
		map.put("compare_column", "to_char(login_date, 'hh24')");
		map.put("action", "loginCountHour");
		List<Map<String, Object>> hour_login_list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 24; i++)
		{
			compare_data = (i < 10 ? "0" : "").concat(Integer.toString(i));
			output_data = compare_data.concat("시");
			map.put("compare_data", compare_data);
			map.put("output_data", output_data);
			Map<String, Object> hour_login = managerDAO.statcount(map);
			hour_login_list.add(hour_login);
		}
		request.setAttribute("hour_login_list", jsonOperate.generateJSONData(lamOperate.changeKeyName(hour_login_list, column_name_y), null, false, false, false, false, false, false, null));

		map.clear();
		map.put("count_column", "id");
		map.put("table", "loginlog");
		map.put("compare_column", "to_char(login_date, 'yyyy-mm-dd')");
		map.put("action", "loginCountWeek");
		List<Map<String, Object>> week_login_list = new ArrayList<Map<String, Object>>();
		for (int i = -6; i <= 0; i++)
		{
			Calendar c = GregorianCalendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, i);
			SimpleDateFormat input_date = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat output_date = new SimpleDateFormat("yyyy/MM/dd");
			compare_data = input_date.format(c.getTime());
			output_data = output_date.format(c.getTime());
			map.put("compare_data", compare_data);
			map.put("output_data", output_data);
			Map<String, Object> week_login = managerDAO.statcount(map);
			week_login_list.add(week_login);
		}
		request.setAttribute("week_login_list", jsonOperate.generateJSONData(lamOperate.changeKeyName(week_login_list, column_name_y), null, false, false, false, false, false, false, null));

		return "managerChartLogin";
	}

	public String chartJoin(Model model, HttpServletRequest request)
	{
		map.clear();
		map.put("count_column", "userid");
		map.put("table", "userinfo");
		map.put("compare_column", "trunc((to_number(to_char(sysdate, 'yyyy')) - to_number(substr(birth, 0, 4))) / 10, 0) * 10");
		map.put("action", "joinCountAge");
		List<Map<String, Object>> age_join_list = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 6; i++)
		{
			switch(i)
			{
				case 1 :
					compare_data = "10";
					output_data = compare_data.concat("대 이하");
					break;
				case 5 :
					compare_data = "50";
					output_data = compare_data.concat("대 이상");
					break;
				default :
					compare_data = Integer.toString(i * 10);
					output_data = compare_data.concat("대");
			}

			map.put("compare_data", compare_data);
			map.put("output_data", output_data);

			Map<String, Object> age_join = managerDAO.statcount(map);
			age_join_list.add(age_join);
		}
		List<String> key = new ArrayList<String>();
		key.add("type");
		key.add("showInLegend");
		key.add("legendText");
		key.add("toolTipContent");

		List<Object> value = new ArrayList<Object>();
		value.add("stackedBar");
		value.add(true);
		value.add("{ label }");
		value.add("{ label } : { y }명");

		request.setAttribute("age_join_list", jsonOperate.generateJSONData(lamOperate.addKeyName(lamOperate.wrappingObject(lamOperate.changeType(lamOperate.wrappingObject(lamOperate.changeKeyName(age_join_list, column_name_y))), "dataPoints"), key, value, false, false, 0, 1, false), null, false, false, false, false, false, false, null));

		map.clear();
		map.put("count_column", "userid");
		map.put("table", "userinfo");
		map.put("compare_column", "gender");
		map.put("action", "joinCountGender");
		List<Map<String, Object>> gender_join_list = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		gender_loop : while (true)
		{
			if (flag) compare_data = "남";
			else compare_data = "여";
			flag = !flag;
			output_data = compare_data.concat("자");

			map.put("compare_data", compare_data);
			map.put("output_data", output_data);

			Map<String, Object> gender_join = managerDAO.statcount(map);
			gender_join_list.add(gender_join);

			if (flag) break gender_loop;
		}
		request.setAttribute("gender_join_list", jsonOperate.generateJSONData(lamOperate.changeKeyName(gender_join_list, column_name_y), null, false, false, false, false, false, false, null));

		return "managerChartJoin";
	}

	@ResponseBody @RequestMapping(value = "/manager/userinfo.ajax", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String userInfo(HttpServletRequest request, @RequestParam(value = "status") String status, @RequestParam(value = "gender") String gender, @RequestParam(value = "search") String search, @RequestParam(value = "value") String value, @RequestParam(value = "page") Integer page)
	{
		StringBuffer sb = new StringBuffer(), sb2 = new StringBuffer();
		value = value == null || value.equals("") ? null : value;
		List<String> status_list = new ArrayList<String>();
		if (status.equals("all") || status.equals("block")) status_list.add("b");
		if (status.equals("all") || status.equals("current")) status_list.add("c");
		List<String> gender_list = new ArrayList<String>();
		if (gender.equals("all") || gender.equals("man")) gender_list.add("남");
		if (gender.equals("all") || gender.equals("woman")) gender_list.add("여");

		map.clear();
		map.put("select_column", "userid, name, birth, gender, email, status");
		map.put("status", status_list);
		map.put("gender", gender_list);
		map.put("compare_column", search);
		map.put("value", value);

		pres_page = page.intValue();
		int member_num = managerDAO.membercount(map);
		int max_page = ((int) member_num / max_element) + (member_num % max_element == 0 ? 0 : 1);
		int begin_page = (pres_page <= 1 + page_div || 1 + page_div * 2 >= max_page ? 1 : (pres_page + page_div >= max_page ? max_page - page_div * 2 : pres_page - page_div));
		int end_page = (pres_page >= max_page - page_div || 1 >= max_page - page_div * 2 ? max_page : (pres_page <= 1 + page_div ? 1 + page_div * 2 : pres_page + page_div));
		int rownum_start_idx = max_element * (pres_page - 1);
		boolean flag;

		List<UserInfoDTO> list = managerDAO.selectmemberinfo(map, rownum_start_idx, max_element);

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
			for (int i = 0; i < list.size(); i++)
			{
				UserInfoDTO data = list.get(i);
				status = data.getStatus();

				sb = sb.append("<tr align=\"center\">")
								.append("<td>".concat(Integer.toString((member_num - (rownum_start_idx + i)))).concat("</td>"))
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
									.append("<li class=\"").append((flag = pres_page == 1) ? "none_a" : "page\" id=\"first-page\"><a href=\"#\" onclick=\"setLink(null, 'manager', 'userinfo', { 'page' : 1})").append("\">start<").append(flag ? "" : "/a><").append("/li>")
								.append("</div>")
							.append("<div id=\"middle\">");

			for (int i = begin_page; i <= end_page; i++) sb2 = sb2.append("<li class=\"").append((flag = pres_page == i) ? "none_a" : "page\"><a href=\"#\" onclick=\"setLink(null, 'manager', 'userinfo', { 'page' : " + i + " })").append("\">").append(i).append("<").append(flag ? "" : "/a><").append("/li>");

			sb2 = sb2.append("</div>")
								.append("<div id=\"end\">")
									.append("<li class=\"").append((flag = pres_page == max_page) ? "none_a" : "page\" id=\"end-page\"><a href=\"#\" onclick=\"setLink(null, 'manager', 'userinfo', { 'page' : " + max_page + " })").append("\">end<").append(flag ? "" : "/a><").append("/li>")
								.append("</div>")
							.append("</ul>");
		}
		else
		{
			sb = sb.append("<tr align=\"center\">")
							.append("<td colspan=\"7\">검색 결과가 없습니다.</td>")
						.append("</tr>");
		}

		return sb.toString().concat("|").concat(sb2.toString());
	}
}
