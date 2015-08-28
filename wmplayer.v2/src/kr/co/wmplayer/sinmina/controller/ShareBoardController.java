package kr.co.wmplayer.sinmina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import kr.co.wmplayer.sinmina.dao.board.ShareboardDAO;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ListMap;
import util.StringOperate;
import youtube.YoutubeSearch;

@Controller
@RequestMapping(value = "/share")
public class ShareBoardController
{
	@Autowired ShareboardDAO shareboardDAO;

	StringOperate so = StringOperate.getInstance();
	Map<String, Object> map;

	int max_element = 9, page_div = 4;

	@RequestMapping(value = "/temp", method = RequestMethod.GET)
	public String temp(Model model, HttpServletRequest request)
	{
		return main(model, request, null);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String main(Model model, HttpServletRequest request, BoardUserDTO bean)
	{
		Object userid = request.getSession().getAttribute("success");

		if (userid != null)
		{
			String action = request.getParameter("action");

			if (action == null || action.equals("list")) return shareList(model, request);
			else if (action.equals("content")) return shareContent(model, request, bean);
			else if (action.equals("write")) return shareWrite(model, request);
			else if (action.equals("data_insert")) return shareInsert(model, request, bean);
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
		System.out.println(bean.getBoard_seq());

		return "sharedetail";
	}

	public String shareWrite(Model model, HttpServletRequest request)
	{
		return "sharewrite";
	}

	public String shareInsert(Model model, HttpServletRequest request, BoardUserDTO bean)
	{
		YoutubeSearch youtubeSearch = YoutubeSearch.getInstance();

		String id = (String) request.getSession().getAttribute("success");
		bean.setBoard_title(so.toUpperCase(bean.getBoard_title(), 0));
		bean.setBoard_artist(so.toUpperCase(bean.getBoard_artist(), 0));
		try
		{
			String videoID = youtubeSearch.getYoutubeId(bean.getBoard_title(), bean.getBoard_artist());
			String albumcover = youtubeSearch.getThumnailAddr(bean.getBoard_title(), bean.getBoard_artist());

			if (videoID == null || albumcover == null)
			{
				request.setAttribute("fail", "fail");
				return shareWrite(model, request);
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
			e.printStackTrace();
			request.setAttribute("fail", "error");
			return shareWrite(model, request);
		}
	}

	@ResponseBody @RequestMapping(value = "/listdata", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String shareData(@RequestParam(value = "weather_custom") String weather_custom, @RequestParam(value = "page") Integer page)
	{
		StringBuffer sb = new StringBuffer();
		String[] weather = (weather_custom == null ? "all" : weather_custom).split(",");
		List<String> weather_list = new ArrayList<String>();
		map = new ListMap<String, Object>();
		map.put("select_column", "*");
		map.put("group_column", null);
		map.put("table", "board_user");
		map.put("column", null);
		map.put("sign", null);
		map.put("data", null);
		int count_element = ((BigDecimal) shareboardDAO.count(map).get("cnt")).intValue();

		int pres_page = page == null ? 1 : page.intValue();
		int start_idx = (pres_page - 1) * max_element;
		int max_page = ((int) count_element / max_element) + (count_element % max_element == 0 ? 0 : 1);
		int begin_page = (pres_page <= 1 + page_div || 1 + page_div * 2 >= max_page ? 1 : (pres_page + page_div >= max_page ? max_page - page_div * 2 : pres_page - page_div));
		int end_page = (pres_page >= max_page - page_div || 1 >= max_page - page_div * 2 ? max_page : (pres_page <= 1 + page_div ? 1 + page_div * 2 : pres_page + page_div));
		boolean flag;

		weather : for (int i = 0; i < weather.length; i++)
		{
			String temp = weather[i];
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

		map.put("select_column", "*");
		map.put("get", "all");
		map.put("weather_custom", weather_list);

		List<BoardUserDTO> list = shareboardDAO.select(map, start_idx, max_element);
		sb = sb.append("<div id=\"card\">");
		for (BoardUserDTO bean : list)
		{
			sb = sb.append("<div class=\"cardtotal\">")
							.append("<li>")
								.append("<a href=\"#\" onclick=\"setLink(null, 'share', 'content', { 'board_seq' : " + bean.getBoard_seq() + " })\">")
									.append("<div class=\"cardimg\">")
										.append("<img id=\"image\" src=\"" + bean.getAlbumcover() + "\" />")
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

		sb = sb.append("</div>")
					.append("<div class=\"searchboard\">")
						.append("<div class=\"search\">")
							.append("<select class=\"search-select\">")
								.append("<option value=\"제목\">제목</option>")
								.append("<option value=\"아티스트\">아티스트</option>")
								.append("<option value=\"작성자\">작성자</option>")
							.append("</select>")
							.append("<input class=\"input-text\" type=\"text\" name=\"search\" placeholder=\"검색어를 입력해주세요\" /><input class=\"input-button search-button\" type=\"button\" value=\"Search\" onclick=\"\" />")
							.append("<div class=\"write\">")
								.append("<input type=\"button\" class=\"search-button\" id=\"share\" value=\"Write\" onclick=\"setLink(null, 'share', 'write')\" />")
							.append("</div>")
						.append("</div>")
					.append("</div>")
					.append("<div class=\"pager-container-share\">")
						.append("<ul class=\"page-list\">")
							.append("<div id=\"first\">")
								.append("<li class=\"").append((flag = pres_page == 1) ? "none_a" : "page\" id=\"first-page\"><a href=\"#\" onclick=\"setLink(null, 'share', 'list', { 'page' : 1})").append("\">start<").append(flag ? "" : "/a><").append("/li>")
							.append("</div>")
							.append("<div id=\"middle\">");

			for (int i = begin_page; i <= end_page; i++) sb = sb.append("<li class=\"").append((flag = pres_page == i) ? "none_a" : "page\"><a href=\"#\" onclick=\"setLink(null, 'share', 'list', { 'page' : " + i + " })").append("\">").append(i).append("<").append(flag ? "" : "/a><").append("/li>");

			sb = sb.append("</div>")
						.append("<div id=\"end\">")
							.append("<li class=\"").append((flag = pres_page == max_page) ? "none_a" : "page\" id=\"end-page\"><a href=\"#\" onclick=\"setLink(null, 'share', 'list', { 'page' : " + max_page + " })").append("\">end<").append(flag ? "" : "/a><").append("/li>")
						.append("</div>")
					.append("</ul>")
				.append("</div>");
		return sb.toString();
	}

	@ResponseBody @RequestMapping(value = "/reple", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String reple()
	{
		return "";
	}
}
