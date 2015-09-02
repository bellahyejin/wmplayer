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
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
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

	BoardUserDTO bean;

	List<String> seq_list;
	StringOperate so = StringOperate.getInstance();
	Map<String, Object> map;
	String fail = "";

	int max_element = 9, page_div = 4;

	@RequestMapping(value = "/temp", method = RequestMethod.GET)
	public String temp(Model model, HttpServletRequest request, HttpSession session)
	{
		return main(model, request, session, null, null);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String main(Model model, HttpServletRequest request, HttpSession session, RedirectAttributes reAttr, BoardUserDTO bean)
	{
		Object userid = session.getAttribute("success");
		map = new HashMap<String, Object>();

		if (userid != null)
		{
			this.bean = bean;
			String action = request.getParameter("action");

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

		String artist = so.toUpperCase(bean.getBoard_artist(), 0);
		String title = so.toUpperCase(bean.getBoard_title(), 0);
		String content = bean.getBoard_desc();
		String weather = bean.getWeather_custom();
		if (artist == null || title == null || content == null || artist.equals("") || title.equals("") || content.equals("") || weather.equals(""))
		{
			map.put("fail", "blank");
			reAttr.addFlashAttribute("data", map);
			return "redirect:share/write";
		}

		YoutubeSearch youtubeSearch = YoutubeSearch.getInstance();

		String id = (String) request.getSession().getAttribute("success");

		try
		{
			String videoID = youtubeSearch.getYoutubeId(bean.getBoard_title(), bean.getBoard_artist());
			String albumcover = youtubeSearch.getThumnailAddr(bean.getBoard_title(), bean.getBoard_artist());

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

				if (shareboardDAO.insert(bean)) return shareList(model, request);
				else return shareWrite(model, request);
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
				sb = sb.append("<div class=\"cardtotal\"")
							.append("<li>")
								.append("<a href=\"#\" onclick=\"setLink(null, 'share', 'content', { 'board_seq' : ").append(bean.getBoard_seq()).append(" })\">")
									.append("<div class=\"cardimg\">")
										.append("<img id=\"image\" src=\"").append(bean.getAlbumcover()).append("\" />")
									.append("</div>")
									.append("<div class=\"back\">")
										.append("<table>")
											.append("<tr>")
												.append("<td id=\"title\" colspan=\"2\">").append(bean.getBoard_title()).append("</td>")
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
												.append("<td id=\"writedate\">").append(bean.getRegi_day()).append("</td>")
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
				if (search_text != null) search_text = search_text.replaceAll("%", "");
				else search_text = "";

				String temp = "\"><a href=\"#\" onclick=\"setLink(null, 'share', 'list', { 'page' : ";
				String temp2 = ", 'weather_custom' : '" + weather_custom + "', 'search_select' : '" + search_select + "', 'search_text' : '" + search_text;

				sb2 = sb2.append("<ul class=\"page-list\">")
								.append("<div id=\"first\">")
									.append("<li class=\"").append((flag = pres_page == 1) ? "none_a" : "page\" id=\"first-page".concat(temp).concat(Integer.toString(1)).concat(temp2).concat("' })")).append("\">start<").append(flag ? "" : "/a><").append("/li>")
								.append("</div>")
								.append("<div id=\"middle\">");
				for (int i = page_data.get("start"); i <= page_data.get("end"); i++) sb2 = sb2.append("<li class=\"").append((flag = pres_page == i) ? "none_a" : temp.concat(Integer.toString(i)).concat(temp2).concat("' })")).append("\">").append(i).append("<").append(flag ? "" : "/a><").append("/li>");
				sb2 = sb2.append("</div>")
							.append("<div id=\"end\">")
								.append("<li class=\"").append((flag = pres_page == max_page) ? "none_a" : "page\" id=\"end-").append(temp).append(max_page).append(temp2).append("' })").append("\">end<").append(flag ? "" : "/a><").append("/li>")
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

	@ResponseBody @RequestMapping(value = "/replelist", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String repleList()
	{
		return "";
	}
}
