package kr.co.wmplayer.sinmina.controller;

import java.sql.Timestamp;
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
import util.Paging;
import util.StringOperate;
import util.Time;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController
{
	@Autowired ManagerDAO managerDAO;

	JSONOperate jsonOperate = JSONOperate.getInstance();
	ListAndMapOperate lamOperate = ListAndMapOperate.getInstance();
	Paging p = Paging.getInstance();
	StringOperate so = StringOperate.getInstance();
	Map<String, Object> column_name_y, map;
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

	int max_element = 15, page_div = 4, pres_page = 1;
	String compare_data, output_data;

	@RequestMapping(value = "/temp", method = RequestMethod.GET)
	public String temp(Model model, HttpServletRequest request)
	{
		return main(model, request);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
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
			SimpleDateFormat input_date = new SimpleDateFormat("HH");
			SimpleDateFormat output_date = new SimpleDateFormat("HH시");
			long time = (i - 9) * Time.getTime(Time.Field.Minute);
			compare_data = input_date.format(time);
			output_data = output_date.format(time);
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
			compare_data = Integer.toString(i * 10);
			output_data = compare_data.concat("대");

			switch(i)
			{
				case 1 :
					output_data = output_data.concat(" 이하");
					break;
				case 5 :
					output_data = output_data.concat(" 이상");
					break;
				default :
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

	@ResponseBody @RequestMapping(value = "/userinfo.ajax", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String userInfo(@RequestParam(value = "status") String status, @RequestParam(value = "gender") String gender, @RequestParam(value = "search") String search, @RequestParam(value = "value") String value, @RequestParam(value = "page") Integer page)
	{
		value = value == null || value.equals("") ? null : value;

		StringBuffer sb = new StringBuffer(), sb2 = new StringBuffer();

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

		Map<String, Integer> page_data = p.setData(member_num, pres_page, max_element, page_div, "begin&last", false);
		boolean flag;

		int start_idx = page_data.get("item_start");
		List<UserInfoDTO> list = managerDAO.selectmemberinfo(map, start_idx, max_element);

		sb = sb.append("<tr class=\"manager_title\">")
						.append("<th width=\"8%\">번호</th>")
						.append("<th width=\"12%\">아이디</th>")
						.append("<th width=\"8%\">이 름</th>")
						.append("<th width=\"10%\">생년월일</th>")
						.append("<th width=\"4%\">성별</th>")
						.append("<th width=\"28%\">이메일</th>")
						.append("<th width=\"10%\"></th>")
					.append("</tr>");

		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				UserInfoDTO data = list.get(i);
				status = data.getStatus();
				String[] birth = data.getBirth().split("/");
				Timestamp time = Timestamp.valueOf(birth[0] + "-" + birth[1] + "-" + birth[2] + " 00:00:00");
				String birth_date = date.format(time.clone());

				sb = sb.append("<tr align=\"center\">")
								.append("<td>").append(Integer.toString((member_num - (start_idx + i)))).append(("</td>"))
								.append("<td>").append(data.getUserID()).append(("</td>"))
								.append("<td>").append(so.substring(data.getName(), 0, 8, false, true)).append(("</td>"))
								.append("<td>").append(birth_date).append(("</td>"))
								.append("<td>").append(data.getGender()).append(("</td>"))
								.append("<td>").append(data.getEmail()).append(("</td>"))
								.append("<td>")
									.append("<input type=\"button\" class=\"button\" name=").append((status.equals("b") ? "current" : "block")).append(("-")).append((data.getUserID())).append((" style=\"font: 9pt\" value=")).append((status.equals("b") ? "활성" : "차단") + ">")
									.append("<input type=\"button\" class=\"button\" name=\"drop-").append((data.getUserID())).append(("\" style=\"font: 9pt\" value=\"탈퇴\">"))
							.append("</tr>");
			}

			int max_page = page_data.get("last");

			if (max_page > 1)
			{
				value = value == null ? "" : value;

				String temp = "page\"><a href=\"#\" onclick=\"setLink(null, 'manager', 'userinfo', { 'page' : ";
				String temp2 = ", 'status' : '" + status + "', 'gender' : '" + gender + "', 'search' : '" + search + "', 'value' : '" + value + "' })";

				sb2 = sb2.append("<ul class=\"page-list\">")
									.append("<div id=\"first\">")
										.append("<li class=\"").append((flag = pres_page == 1) ? "none_a" : "page\" id=\"first-" + temp + "1" + temp2).append("\">start<").append(flag ? "" : "/a><").append("/li>")
									.append("</div>")
								.append("<div id=\"middle\">");
				for (int i = page_data.get("start"); i <= page_data.get("end"); i++) sb2 = sb2.append("<li class=\"").append((flag = pres_page == i) ? "none_a" : temp + i + temp2).append("\">").append(i).append("<").append(flag ? "" : "/a><").append("/li>");
				sb2 = sb2.append("</div>")
									.append("<div id=\"end\">")
										.append("<li class=\"").append((flag = pres_page == max_page) ? "none_a" : "page\" id=\"end-" + temp + max_page + temp2).append("\">end<").append(flag ? "" : "/a><").append("/li>")
									.append("</div>")
								.append("</ul>");
			}
		}
		else
		{
			sb = sb.append("<tr align=\"center\">")
							.append("<td colspan=\"7\">검색 결과가 없습니다.</td>")
						.append("</tr>");
		}

		return sb.append("|").append(sb2).toString();
	}
}
