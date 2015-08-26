package kr.co.wmplayer.sinmina.ajax.manager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.wmplayer.sinmina.dao.manager.ManagerDAO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.ListMap;

public class MemberInfoAction extends Action
{
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		ManagerDAO dao = new ManagerDAO();
		StringBuffer sb = new StringBuffer(), sb2 = new StringBuffer();
		String status = request.getParameter("status");
		String gender = request.getParameter("gender");

		List<String> status_list = new ArrayList<String>();
		if (status.equals("all") || status.equals("block")) status_list.add("b");
		if (status.equals("all") || status.equals("current")) status_list.add("c");

		List<String> gender_list = new ArrayList<String>();
		if (gender.equals("all") || gender.equals("man")) gender_list.add("남");
		if (gender.equals("all") || gender.equals("woman")) gender_list.add("여");

		ListMap<String, Object> map = new ListMap<String, Object>();
		map.put("select_column", "userId, name, birth, gender, email, status");
		map.put("status", status_list);
		map.put("gender", gender_list);
		map.put("compare_column", request.getParameter("search"));
		map.put("value", request.getParameter("value"));

		int member_num = dao.membercount(map);
		int max_element = (Integer) session.getAttribute("max_element");
		int page_div = (Integer) session.getAttribute("page_div");
		int pres_page = Integer.parseInt(request.getParameter("page"));
		int max_page = ((int) member_num / max_element) + (member_num % max_element == 0 ? 0 : 1);
		int begin_page = (pres_page <= 1 + page_div || 1 + page_div * 2 >= max_page ? 1 : (pres_page + page_div >= max_page ? max_page - page_div * 2 : pres_page - page_div));
		int end_page = (pres_page >= max_page - page_div || 1 >= max_page - page_div * 2 ? max_page : (pres_page <= 1 + page_div ? 1 + page_div * 2 : pres_page + page_div));
		int rownum_start_idx = max_element * (pres_page - 1), count = 0;
		boolean flag;

		List<UserInfoDTO> list = dao.selectmemberinfo(map, rownum_start_idx, max_element);

		sb = sb.append("<tr class='manager_title'>")
				.append("<th width=\"8%\">번호</th>")
				.append("<th width=\"12%\">아이디</th>")
				.append("<th width=\"10%\">이 름</th>")
				.append("<th width=\"12%\">생년월일</th>")
				.append("<th width=\"5%\">성별</th>")
				.append("<th width=\"20%\">이메일</th>")
				.append("<th width=\"10%\"></th>")
				.append("</tr>");

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

		response.setContentType("html");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(sb.append("|").append(sb2.toString()).toString());
		return super.execute(mapping, form, request, response);
	}
}
