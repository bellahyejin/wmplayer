<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String weather_custom = request.getParameter("weather_custom");
	String weather = "";
	
	if(weather_custom.equals("")){
		weather = "all";
	}
	else {
		weather = weather_custom.substring(0, weather_custom.length()-1);
	}
	
	String data = "";
	ShareboardDAO dao = new ShareboardDAO();
	
	int size = 0; //데이터 전체 사이즈
	int num = request.getParameter("page") == null ? 1 : (9*(Integer.parseInt(request.getParameter("page"))-1)); //start
	System.out.println("ssss:::" +num);
	if(weather.contains("all") || weather == null) {
		data = "'맑음','비','눈','흐림','바람'";
	}else{
		data = weather;
	}
	List<BoardUserDTO> list = dao.selectSortboard(data, num);
	size = dao.dataSize(data);
	
	request.setAttribute("cardlist", list);	
	System.out.println("size:::"+ list.size()+" datat:::"+data);
	
	int page_max = 9;
	int page_div = 4;

	int pres_page = request.getParameter("page") == null ? 1 : Integer
			.parseInt(request.getParameter("page").toString());
	int max_page = ((int) size / page_max)
			+ (size % page_max == 0 ? 0 : 1);
	int begin_page = (pres_page <= 1 + page_div
			|| 1 + page_div * 2 >= max_page ? 1
			: (pres_page + page_div >= max_page ? max_page - page_div
					* 2 : pres_page - page_div));
	int end_page = (pres_page >= max_page - page_div
			|| 1 >= max_page - page_div * 2 ? max_page
			: (pres_page <= 1 + page_div ? 1 + page_div * 2 : pres_page
					+ page_div));

	request.setAttribute("begin_page", begin_page);
	request.setAttribute("pres_page", pres_page);
	request.setAttribute("end_page", end_page);
	request.setAttribute("max_page", max_page);
%>
<script type="text/javascript">
$(document).ready(function(){
	$('a').click(function(){
		flag =false;	
		alert($(this).attr("id"))
		pagetrans($(this).attr("id"));	
	});
});
</script>
<div id="card">
<c:forEach items="${cardlist }" var="list">
		<div class="cardtotal">
			<li><a
				href="${initParam.root }/wmplayer/sharedetail/view.do?board_seq=${ list.board_seq}"
				target="section">
					<div class="cardimg">
						<img id="image" src="${ list.albumcover}">
					</div>
					<div class="back">
						<table>
							<tr>
								<td id="title" colspan="2">${ list.board_title }</td>
							</tr>
							<tr>
								<td class="info_title left up">Artist</td>
								<td id="artist">${list.board_artist }</td>
							</tr>
							<tr>
								<td class="info_title left">Weather</td>
								<td id="gerne">${ list.weather_custom }</td>
							</tr>
							<tr>
								<td class="writeinfo left">${ list.userID }</td>
								<td id="writedate">${list.regi_day }</td>
							</tr>
						</table>
					</div>
			</a></li>
		</div>
</c:forEach>
</div>
<div class="searchboard">
	<div class="search">
		<select class="search-select" onclick="">
			<option value="제목">제목</option>
			<option value="아티스트">아티스트</option>
			<option value="작성자">작성자</option>
		</select> <input class="input-text" type="text" name="search"
			placeholder="검색어를 입력해주세요"> <input
			class="input-button search-button" type="button" value="Search"
			onclick="">
		<div class="write">
			<input type="button" class="search-button" id="share" value="Write"
				onclick="location.href='${initParam.root}/wmplayer/sharewrite.do'" />
		</div>
	</div>
</div>
<div class="pager-container-share">
	<ul class="page-list">
		<div id="first">
			<c:choose>
				<c:when test="${ pres_page == 1 }">
					<li class="none_a">start</li>
				</c:when>
				<c:otherwise>
					<li class="page" id="first-page"><a class="page-num" href="#" id="1">start</a></li>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="middle">
			<c:forEach begin="${ begin_page }" end="${ end_page }" var="page">
				<c:choose>
					<c:when test="${ pres_page != page }">
						<li class="page"><a class="page-num" href="#" id="${page }">${ page }</a></li>
					</c:when>
					<c:otherwise>
						<li class="none_a">${ page }</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		<div id="end">
			<c:choose>
				<c:when test="${ pres_page == max_page }">
					<li class="none_a">end</li>
				</c:when>
				<c:otherwise>
					<li class="page" id="end-page"><a class="page-num" href="#" id='${ max_page }'>end</a></li>
				</c:otherwise>
			</c:choose>
		</div>
	</ul>
</div>
