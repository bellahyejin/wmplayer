package kr.co.wmplayer.sinmina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;
import kr.co.wmplayer.sinmina.dao.reply.ShareReplyDAO;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import kr.co.wmplayer.sinmina.model.dto.reply.ShareReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import util.ListMap;
import util.Paging;
import util.StringOperate;
import youtube.YoutubeSearch;

@Controller
@RequestMapping(value = "/share")
public class ShareBoardController
{
	@Autowired ShareboardDAO shareboardDAO;
	@Autowired ShareReplyDAO sharereplyDAO;

	List<String> seq_list;
	StringOperate so = StringOperate.getInstance();
	Map<String, Object> map;
	String fail = "";

	int max_element = 9, page_div = 4;

	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	public String main(Model model, HttpServletRequest request, HttpSession session, RedirectAttributes reAttr, BoardUserDTO bean, @RequestParam(value = "action", required = false) String action)
	{
		Object userid = session.getAttribute("success");
		map = new HashMap<String, Object>();

		if (userid != null)
		{
			if (action == null || action.equals("list")) return shareList(model, request);
			else if (action.equals("content")) return shareContent(model, request, bean);
			else if (action.equals("write")) return shareWrite(model, request);
			else if (action.equals("data_insert")) return shareInsert(model, request, reAttr, bean);
			else return null;
		}
		else return "redirect:/intro";
	}

	public String shareList(Model model, HttpServletRequest request)
	{
		int pres_page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		request.setAttribute("pres_page", pres_page);
		return "sharelist";
	}

	public String shareContent(Model model, HttpServletRequest request, BoardUserDTO bean)
	{
		int content_idx = 0, prev_content = 0, next_content = 0;

		List<String> weather_list = getWeather("all");

		map.clear();
		map.put("select_column", "board_seq");
		map.put("get", "all");
		map.put("weather_custom", weather_list);
		List<BoardUserDTO> seq_list = shareboardDAO.select(map, 0, 0);

		map.clear();
		map.put("action", "count");
		map.put("share_seq", bean.getBoard_seq());
		shareboardDAO.update(map);

		map.clear();
		map.put("select_column", "*");
		map.put("get", "one");
		map.put("compare_column", "board_seq");
		map.put("value", bean.getBoard_seq());
		List<BoardUserDTO> list = shareboardDAO.select(map, 0, 0);

		for (BoardUserDTO temp : seq_list)
		{
			if (temp.getBoard_seq() == bean.getBoard_seq())
			{
				content_idx = seq_list.indexOf(temp);
				break;
			}
		}

		if (content_idx != 0) next_content = seq_list.get(content_idx - 1).getBoard_seq();
		if (content_idx != seq_list.size() - 1) prev_content = seq_list.get(content_idx + 1).getBoard_seq();

		request.setAttribute("data", list.get(0));
		request.setAttribute("prev_content", prev_content);
		request.setAttribute("next_content", next_content);
		return "sharedetail";
	}

	@RequestMapping(value = "/write")
	public String shareWrite(Model model, HttpServletRequest request)
	{
		return "sharewrite";
	}

	public String shareInsert(Model model, HttpServletRequest request, RedirectAttributes reAttr, BoardUserDTO bean)
	{
		map.clear();
		map.put("bean", bean);

		String artist = bean.getBoard_artist();
		String title = bean.getBoard_title();
		String content = bean.getBoard_desc();
		String weather = bean.getWeather_custom();
		System.out.println(artist + " " + title + " " + content + " " + weather);
		if (artist == null || title == null || content == null || artist.equals("") || title.equals("") || content.equals("") || weather.equals(""))
		{
			map.put("fail", "blank");
			reAttr.addFlashAttribute("data", map);
			return "redirect:share/write";
		}

		artist = so.toUpperCase(bean.getBoard_artist(), 0);
		title = so.toUpperCase(bean.getBoard_title(), 0);

		YoutubeSearch youtubeSearch = YoutubeSearch.getInstance();

		String id = (String) request.getSession().getAttribute("success");

		try
		{
			String videoID = youtubeSearch.getYoutubeId(bean.getBoard_title(), bean.getBoard_artist());
			String albumcover = youtubeSearch.getThumnailAddr(bean.getBoard_title(), bean.getBoard_artist());

			System.out.println(videoID + " " + albumcover);

			if (videoID == null || albumcover == null)
			{
				map.put("fail", "fail");
				reAttr.addFlashAttribute("data", map);
				return "redirect:share/write";
			}
			else
			{
				bean.setUserID(id);
				bean.setVideoID(videoID);
				bean.setAlbumcover(albumcover);

				if (shareboardDAO.insert(bean)) return "redirect:share";
				else return "redirect:share/write";
			}
		}
		catch (IOException | InterruptedException e)
		{
			map.put("fail", "error");
			reAttr.addFlashAttribute("data", map);
			return "redirect:share/write";
		}
	}

	@ResponseBody @RequestMapping(value = "/listdata.ajax", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String shareData(@RequestParam(value = "weather_custom") String weather_custom, @RequestParam(value = "search_select", required = false) String search_select, @RequestParam(value = "search_text", required = false) String search_text, @RequestParam(value = "page") Integer page)
	{
		weather_custom = weather_custom == null || weather_custom.equals("") ? "all" : weather_custom;
		search_text = (search_text == null || search_text.equals("") ? null : "%" + search_text + "%");

		Paging p = Paging.getInstance();
		StringBuffer sb = new StringBuffer(), sb2 = new StringBuffer();
		List<String> weather_list = getWeather(weather_custom);
		map = new ListMap<String, Object>();
		map.put("select_column", "*");
		map.put("group_column", null);
		map.put("table", "board_user");
		map.put("column", search_select);
		map.put("sign", "like");
		map.put("data", search_text);
		map.put("list_compare_column", "weather_custom");
		map.put("list_value", weather_list);
		int count_element = ((BigDecimal) shareboardDAO.count(map).get("cnt")).intValue();
		int pres_page = page == null ? 1 : page.intValue();

		Map<String, Integer> page_data = p.setData(count_element, pres_page, max_element, page_div, "begin&last", false);
		boolean flag;

		map.clear();
		map.put("select_column", "*");
		map.put("get", "all");
		map.put("weather_custom", weather_list);
		map.put("compare_column", search_select);
		map.put("value", search_text);

		List<BoardUserDTO> list = shareboardDAO.select(map, page_data.get("item_start"), max_element);
		sb = sb.append("<div id=\"card\">");
		if (list != null && list.size() > 0)
		{
			for (BoardUserDTO bean : list)
			{
				sb = sb.append("<div class=\"cardtotal\">")
							.append("<li>")
								.append("<a href=\"#\" onclick=\"setLink(null, 'share', 'content', { 'board_seq' : ").append(bean.getBoard_seq()).append(" })\">")
									.append("<div class=\"cardimg\">")
										.append("<img id=\"image\" src=\"").append(bean.getAlbumcover()).append("\" />")
									.append("</div>")
									.append("<div class=\"back\">")
										.append("<table>")
											.append("<tr>")
												.append("<td id=\"title\" colspan=\"2\">").append(so.substring(bean.getBoard_title(), 0, 10, false, true)).append("</td>")
											.append("</tr>")
											.append("<tr>")
												.append("<td class=\"info_title left up\">Artist</td>")
												.append("<td id=\"artist\">").append(bean.getBoard_artist()).append("</td>")
											.append("</tr>")
											.append("<tr>")
												.append("<td class=\"info_title left\">Weather</td>")
												.append("<td id=\"gerne\">").append(bean.getWeather_custom()).append("</td>")
											.append("</tr>")
											.append("<tr>")
												.append("<td class=\"writeinfo left\">").append(bean.getUserID()).append("</td>")
												.append("<td id=\"writedate\">").append(bean.getRegi_day().replaceAll("-", "/").substring(0,10)).append("</td>")
											.append("</tr>")
										.append("</table>")
									.append("</div>")
								.append("</a>")
							.append("</li>")
						.append("</div>");
			}

			int max_page = page_data.get("last");

			if (max_page > 1)
			{
				search_text = search_text == null ? "" : search_text.replaceAll("%", "");

				String temp = "\"><a href=\"#\" onclick=\"setLink(null, 'share', 'list', { 'page' : ";
				String temp2 = ", 'weather_custom' : '" + weather_custom + "', 'search_select' : '" + search_select + "', 'search_text' : '" + search_text + "' })";

				sb2 = sb2.append("<ul class=\"page-list\">")
								.append("<div id=\"first\">")
									.append("<li class=\"").append((flag = pres_page == 1) ? "none_a" : "page\" id=\"first-page".concat(temp).concat(Integer.toString(1)).concat(temp2)).append("\">start<").append(flag ? "" : "/a><").append("/li>")
								.append("</div>")
								.append("<div id=\"middle\">");
				for (int i = page_data.get("start"); i <= page_data.get("end"); i++) sb2 = sb2.append("<li class=\"").append((flag = pres_page == i) ? "none_a" : temp.concat(Integer.toString(i)).concat(temp2)).append("\">").append(i).append("<").append(flag ? "" : "/a><").append("/li>");
				sb2 = sb2.append("</div>")
							.append("<div id=\"end\">")
								.append("<li class=\"").append((flag = pres_page == max_page) ? "none_a" : "page\" id=\"end-").append(temp).append(max_page).append(temp2).append("\">end<").append(flag ? "" : "/a><").append("/li>")
							.append("</div>")
						.append("</ul>");
			}
		}
		else
		{
			sb = sb.append("<table>")
							.append("<tr align=\"center\">")
								.append("<td>검색 결과가 없습니다.</td>")
							.append("</tr>")
						.append("</table>");
		}

		sb = sb.append("</div>");

		return sb.append("|").append(sb2).toString();
	}

	public List<String> getWeather(CharSequence weather_custom)
	{
		List<String> weather_list = new ArrayList<String>();
		StringTokenizer weather = new StringTokenizer(weather_custom.toString(), ",");

		weather : while (weather.hasMoreTokens())
		{
			String temp = weather.nextToken();
			String kor_weather;

			switch (temp)
			{
				case "all" :
				case "sun" :
					kor_weather = "맑음";
					if (weather_list.indexOf(kor_weather) == -1) weather_list.add(kor_weather);
					if (!temp.equals("all")) break;
				case "rain" :
					kor_weather = "비";
					if (weather_list.indexOf(kor_weather) == -1) weather_list.add(kor_weather);
					if (!temp.equals("all")) break;
				case "snow" :
					kor_weather = "눈";
					if (weather_list.indexOf(kor_weather) == -1) weather_list.add(kor_weather);
					if (!temp.equals("all")) break;
				case "cloud" :
					kor_weather = "흐림";
					if (weather_list.indexOf(kor_weather) == -1) weather_list.add(kor_weather);
					if (!temp.equals("all")) break;
				case "lowcloudy" :
					kor_weather = "바람";
					if (weather_list.indexOf(kor_weather) == -1) weather_list.add(kor_weather);
			}

			if (temp.equals("all")) break weather;
		}

		return weather_list;
	}

	@ResponseBody @RequestMapping(value = "/reple/list.ajax", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String repleList(ShareReplyDTO bean, HttpSession session)//, @RequestParam(value = "pageNo") Integer page)
	{
		String id = (String) session.getAttribute("success");
		/*Paging p = Paging.getInstance();
		page = page == null || page <= 0 ? 1 : page;
		int max_element = 10, page_div = 4;*/
		StringBuffer sb = new StringBuffer();
		sb = sb.append("<table width=\"540px\" class=\"call_click\">");

		/*int total_size = sharereplyDAO.selectAll(bean.getBoard_seq(), -1).size();
		Map<String, Integer> page_data = p.setData(total_size, page, max_element, page_div, "begin&last", false);*/
		List<ShareReplyDTO> list = sharereplyDAO.select(bean.getBoard_seq(), -1);//page_data.get("item_start"));

		if (list == null || list.size() > 0)
		{
			sb = sb.append("<tr>")
							//.append("<th width=\"10%\">번호</th>")
							.append("<th width=\"45%\">내용</th>")
							.append("<th width=\"15%\">작성자</th>")
							.append("<th width=\"25%\">작성일</th>")
							.append("<th width=\"15%\"></th>")
						.append("</tr>");

			for (ShareReplyDTO temp : list)
			{
				sb = sb.append("<tr align=\"center\" data-seq=\"".concat(Integer.toString(temp.getSharereple_seq())).concat("\">"))
								//.append("<td>").append(total_size - max_element * (page - 1) - i).append("</td>")
								.append("<td align=\"left\" id=\"reple-content\">").append(temp.getContents()).append("<br><input type=\"text\" size=\"30\" id=\"update-reple\" value=\"").append(temp.getContents()).append("\"><input class=\"reple-update\" id=\"updatebutton\" type=\"button\" value=\"수정\"></td>")
								.append("<td>").append(temp.getuserID()).append("</td>")
								.append("<td style=\"font-size : small; align : center;\">").append(temp.getSubmit_date().replaceAll("-", "/").substring(0, temp.getSubmit_date().lastIndexOf("."))).append("</td>")
								.append("<td id=\"upDel\" style=\"font-size : small\">").append(temp.getuserID().equals(id) ? "<a class=\"update\" id=\"update\" href=\"#\">수정</a> <a class=\"delete\" id=\"delete\" href=\"#\" title=\"".concat(Integer.toString(temp.getSharereple_seq()).concat("\">삭제</a>")) : "").append("</td>")
							.append("</tr>");
			}
		}
		sb = sb.append("</table>");
		return sb.toString();
	}

	@ResponseBody @RequestMapping(value = "/reple/insert.ajax", method = RequestMethod.POST)
	public String repleInsert(ShareReplyDTO bean, HttpSession session)
	{
		String content = bean.getContents();
		if (content == null || content.equals("")) return "rewrite";
		bean.setuserID((String) session.getAttribute("success"));
		return sharereplyDAO.insert(bean) ? "success" : "failed";
	}

	@ResponseBody @RequestMapping(value = "/reple/update.ajax", method = RequestMethod.POST)
	public String repleUpdate(ShareReplyDTO bean)
	{
		String content = bean.getContents();
		if (content == null || content.equals("")) return "rewrite";
		return sharereplyDAO.update(bean) ? "success" : "failed";
	}

	@ResponseBody @RequestMapping(value = "/reple/delete.ajax", method = RequestMethod.POST)
	public String repleDelete(ShareReplyDTO bean)
	{
		return sharereplyDAO.delete(bean) ? "success" : "failed";
	}
}
