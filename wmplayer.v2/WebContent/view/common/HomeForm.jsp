<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
     pageEncoding="EUC-KR" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/HomeForm.css" rel="stylesheet" />
<script src="${initParam.root }/js/jquery-2.1.4.js"></script>
<script src="${initParam.root }/js/HomeForm.js"></script>
<script src="${initParam.root }/js/jquery.mCustomScrollbar.concat.min.js"></script>
<div class="home-menu">
	<div class="regular" id="notice-list">
		<div class="subject top-margin-subject notice-list-subject">공지사항▶</div>
			<div class="notice">
			<c:forEach items="${ notice }" var="list">
				<span class="notice-subject">
				<span class="attribute">알림</span>
					<a href='${initParam.root }/wmplayer/noticedetail.do?title=${list.get(list.indexOf(notice))}'>${ list }</a>
				</span>
				<br><br>
			</c:forEach>
			<span class="notice-subject">
			<span class="attribute">알림</span>
			<a href='${initParam.root }/wmplayer/noticedetail.do'>${ list.get(0)}</a>
			</span>
		</div>
	</div>
	<div class="subject must-list-subject">오늘의 추천 리스트</div>
	<div id="must-list">
		<jsp:include page="HomeFormCard.jsp"/>
	</div>
	<table class="boardpadding list left-margin-subject" id="music-rank-list">
		<tr id="title">
			<td colspan="3" class="bottom_border"><div class="music-rank-list-subject left-margin-subject">인기 칼럼</div>
			</td>
		</tr>
		<c:forEach items="${column }" var="list">
		<tr class="bottom_border_td">
			<td class="music-rank">
			</td>
			<td class="music-rank-object">
				<a href="${initParam.root }/wmplayer/columndetail.do?column_seq=${list.column_seq}">${list.title }</a>
			</td>
			<td class="music-rank-writer">${list.view_cnts }
			</td>
		</tr>
		</c:forEach>
	</table>
	<table class="board list" id="board-rank-list">
		<tr>
			<td colspan="3" class="bottom_border"><div class="board-rank-list-subject left-margin-subject ">인기 공유 게시글</div>
			</td>
		</tr>
		<c:forEach items="${share }" var="board">
		<tr>
			<td class="board-rank">
			</td>
			<td class="board-rank-subject">
			<a href="${initParam.root }/wmplayer/sharedetail/view.do?board_seq=${board.board_seq}">${board.board_title }-${board.board_artist }</a>
			</td>
			<td class="board-rank-writer">${board.userID }
			</td>
		</tr>
		</c:forEach>		
	</table>
</div>
