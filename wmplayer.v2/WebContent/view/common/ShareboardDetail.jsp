<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link type="text/css" href="${initParam.root }/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root }/css/ShareboardDetail.css" rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<%
	int page_max = 10;
	int page_div = 4;
	ArrayList<String> list2 = new ArrayList<String>();
	for (int i = 0; i < 5; i++)
		list2.add(i, Integer.toString(i + 1));
	request.setAttribute("list_drop", list2);

	int member_num = 1047;
	int pres_page = request.getParameter("page") == null ? 1 : Integer
			.parseInt(request.getParameter("page").toString());
	int max_page = ((int) member_num / page_max)
			+ (member_num % page_max == 0 ? 0 : 1);
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


	ArrayList<String> list = new ArrayList<String>();
	for (int i = 0; i < page_max; i++) {
		int temp2 = member_num % page_max;
		int temp = ((max_page - pres_page - (temp2 == 0 ? 0 : 1)) * page_max)
				+ (page_max - i) + temp2;
		if (temp > 0)
			list.add(i, Integer.toString(temp));
	}
	request.setAttribute("list_member", list);
%>
<%
	ArrayList<String> seqList = (ArrayList<String>) request
			.getAttribute("seqlist");
	int size = seqList.size();
	BoardUserDTO board = (BoardUserDTO) request.getAttribute("detail");
	int nowIndex = seqList.indexOf("" + board.getBoard_seq());
	System.out.print("now : " + nowIndex + "\n");
	int nextIdx = 0;
	int beforeIdx = 0;
	int board_seq = board.getBoard_seq();
	request.setAttribute("board_seq", board_seq);

	if (nowIndex == 0) {
		nextIdx = Integer.parseInt(seqList.get(0));
		beforeIdx = Integer.parseInt(seqList.get(nowIndex + 1));
		out.print("<script>function warning(){alert('다음페이지가 없습니다')");

	} else if (nowIndex == size - 1) {
		nextIdx = Integer.parseInt(seqList.get(nowIndex - 1));
		beforeIdx = Integer.parseInt(seqList.get(size - 1));
		out.print("<script>function warning2(){alert('이전페이지가 없습니다');}</script>");
	} else {
		nextIdx = Integer.parseInt(seqList.get(nowIndex - 1));
		beforeIdx = Integer.parseInt(seqList.get(nowIndex + 1));
	}

	System.out.print("next : " + nextIdx + " before:" + beforeIdx);
	String regi_day = board.getRegi_day();
%>
<script type="text/javascript">
function before(){
	
	location.href="${initParam.root }/wmplayer/sharedetail/view.do?board_seq=<%=beforeIdx %>";
}
function next(){
	
	location.href="${initParam.root }/wmplayer/sharedetail/view.do?board_seq=<%=nextIdx %>";
}
</script>
detail
<script>
var pageNum
$(document).ready(function(){	      
	$("#repleInput").click(function(){
	var txt = $("#repleTxt").val();
	$.post("../ajax/share/InsertReple.jsp",
			{repleContent:txt,
			column_seq:board_seq}, function(data){					
			});		
	});
	
});
</script>

<div class="share-detail">
	<div class="share-detail-header">
		<span><marquee scrollamount="5" behavior="scroll" width="300">${detail.board_title }
				- ${detail.board_artist }</marquee></span>
		<div id="select-info">
			<div id="col-date">
				<span class="column-infotitle">등록일</span> <span id="column-date">
					<%= regi_day.replace("-", "/").substring(0, 10) %>
				</span>
			</div>
			<div id="col-view">
				<span class="column-infotitle">조회수</span> <span id="column-view">${detail.check_cnt }</span>
			</div>
		</div>
	</div>
	<div class="share-detail-section">
		<div class="share-detail-mv">
			<embed width="400" height="300"
				src='//www.youtube.com/embed/${detail.videoID }?autoplay=1&controls=0&showinfo=0&rel=0&wmode="transparent"&allowfullscreen="false"'>
		</div>
		<div class="share-detail-info">
			<table class="share-table">
				<tr>
					<td id="title">날&nbsp;&nbsp;&nbsp;씨</td>
					<td>${detail.weather_custom }</td>
				</tr>
				<tr>
					<td id="title">작성자</td>
					<td>${detail.userID }</td>
				</tr>
				<tr>
					<td colspan="2" id="desc">${detail.board_desc }</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="share-button">
		<div class="paging-column">
			<input type="button" class="styled-button-detail" id="detail"
				value="이 전"
				onclick="before(); warning2();" />
			<input type="button" class="styled-button-detail" id="detail"
				value="다 음"
				onclick="next(); warning()" />
			<input type="button" class="styled-button-list" id="list" value="목 록"
				onclick="location.href='${initParam.root}/wmplayer/sharelist.do'" />
		</div>
		
		<div><!-- 리플시작 -->
<input type="text" size="68" id="repleTxt">
<input type="button" value="확인" id="repleInput">
</div>

<div id="AppendReple" style="size: 800px;">
</div>
		
	</div>
</div>
