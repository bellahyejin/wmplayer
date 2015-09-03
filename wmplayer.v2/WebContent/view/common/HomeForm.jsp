<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/HomeForm.css" rel="stylesheet" />
<script src="${initParam.root }/js/jquery-2.1.4.js"></script>
<script src="${initParam.root }/js/HomeForm.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script src="${initParam.root }/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript">
	var titleLength = 0;

	$(document).ready(function()
		{
			parent.getLocation_cord();
		});

	function loaded(doc) {

		var today_title = doc.getElementsByTagName('title');
		var today_artist = doc.getElementsByTagName('artist');
		var today_image = doc.getElementsByTagName('image');
		var min_bpm = doc.getElementsByTagName('currentMinBpm');
		var max_bpm = doc.getElementsByTagName('currentMaxBpm');

		var cur_min = min_bpm.item(0).firstChild.nodeValue;
		var cur_max = max_bpm.item(0).firstChild.nodeValue;

		var title = document.getElementById('todaymusic_title');
		var artist = document.getElementById('todaymusic_artist');
		var min = document.getElementById('min');
		var max = document.getElementById('max');

		var imgArr = new Array();
		var titleArr = new Array();
		var artistArr = new Array();
		var img_cover = new Array();

		var todaylist_home = document.getElementById("music-jacket-list-all");
		var i = 0;

		do {
			titleArr[i] = today_title.item(i).firstChild.nodeValue;
			artistArr[i] = today_artist.item(i).firstChild.nodeValue;
			imgArr[i] = today_image.item(i).firstChild.nodeValue;

			//dl만들기
			var dlDiv = document.createElement('dl');
			//dt
			var imgDt = document.createElement('dt');
			var titleDd = document.createElement('dd');
			var artistDd = document.createElement('dd');

			img_cover[i] = document.createElement('img');

			//img_cover 속성
			img_cover[i].setAttribute("class", "jacket");
			dlDiv.setAttribute("class", "list");
			imgDt.setAttribute("id", "image");
			titleDd.setAttribute("id", "title");
			artistDd.setAttribute("id", "artist");
			//divAll 속성
			//divScroll 속성
			//dl, dt
			dlDiv.appendChild(imgDt);
			dlDiv.appendChild(titleDd);
			dlDiv.appendChild(artistDd);

			imgDt.appendChild(img_cover[i]);

			//데이터 입력
			img_cover[i].src = imgArr[i];
			titleDd.innerHTML = titleArr[i].length > 10 ? titleArr[i]
					.substring(0, 10)
					+ "..." : titleArr[i];
			artistDd.innerHTML = artistArr[i].length > 10 ? artistArr[i]
					.substring(0, 10)
					+ "..." : artistArr[i];

			todaylist_home.appendChild(dlDiv);
		} while (today_title.item(++i) != null);

		todaylist_home.style.width = imgArr.length * 120;
		min.innerHTML = cur_min;
		max.innerHTML = cur_max;

	}

</script>
<div class="home-menu">
	<div class="regular" id="notice-list">
		<div class="subject top-margin-subject notice-list-subject">공지사항▶</div>
			<div class="notice">
			<c:forEach items="${ notice }" var="list">
				<span class="notice-subject">
				<span class="attribute">알림</span>
					<a href='noticedetail?title=${list}'>${ list }</a>
				</span>
				<br><br>
			</c:forEach>
			<span class="notice-subject">
			<span class="attribute">알림</span>
			<a href='noticedetail?title=${notice}'>${ notice}</a>
			</span>
		</div>
	</div>
	<div class="subject must-list-subject">오늘의 추천 리스트<div id="bpm">현재 추천 BPM | <span id="min"></span> ~ <span id="max"></span></div></div>
	<div id="must-list">
		<jsp:include page="HomeFormCard.jsp"/>
	</div>
	<table class="boardpadding list left-margin-subject" id="music-rank-list">
		<tr id="title">
			<td colspan="3" class="bottom_border"><div class="music-rank-list-subject left-margin-subject">인기 칼럼</div>
			</td>
		</tr>
		<c:forEach items="${column }" var="list" varStatus="status">
		<tr class="bottom_border_td">
			<td class="music-rank">${status.count }
			</td>
			<td class="music-rank-object">
				<a href="columndetail?column_seq=${list.column_seq}">${list.title }</a>
			</td>
			<td class="music-rank-writer">${list.view_cnt }
			</td>
		</tr>
		</c:forEach>
	</table>
	<table class="board list" id="board-rank-list" >
		<tr>
			<td colspan="3" class="bottom_border"><div class="board-rank-list-subject left-margin-subject ">인기 공유 게시글</div>
			</td>
		</tr>
		<c:forEach items="${share }" var="list" varStatus="status">
		<tr>
			<td class="board-rank">${status.count }
			</td>
			<td class="board-rank-subject">
			<a href="#"  onclick="setLink(null, 'share', 'content', { 'board_seq' : ${list.board_seq}})">${list.board_title }-${list.board_artist }</a>
			</td>
			<td class="board-rank-writer">${list.userID }
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
