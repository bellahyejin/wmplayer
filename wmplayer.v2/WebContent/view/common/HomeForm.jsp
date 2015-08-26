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
<%
	NoticeboardDAO noticedao = new NoticeboardDAO();
	List<String> title = noticedao.hometitle(1);

	request.setAttribute("title", title);


	ColumnboardDAO columndao = new ColumnboardDAO();
	List<ColumnBoardDTO> column_rank = columndao.selectHomerank();

	ShareboardDAO sharedao = new ShareboardDAO();
	List<BoardUserDTO> board_rank = sharedao.selectPop();

%>
		<div class="home-menu">
			<div class="regular" id="notice-list">
				<div class="subject top-margin-subject notice-list-subject">공지사항▶</div>
				<div class="notice">
					<c:forEach items="${ title }" var="notice"><!--   for (-- notice : title) for (int i = 0; i < title.si; i++) notice = title.get(i) -->
						<span class="notice-subject">
							<span class="attribute">알림</span>
							<a href='${initParam.root }/wmplayer/noticedetail.do?title=${title.get(title.indexOf(notice))}'>${ notice }</a>
						</span>
						<br><br>
					</c:forEach>
					<span class="notice-subject">
						<span class="attribute">알림</span>
						<a href='${initParam.root }/wmplayer/noticedetail.do'>${ title.get(0)}</a>
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
				<%for( int i = 0 ; i < board_rank.size(); i++){
					int column_seq = column_rank.get(i).getColumn_seq();
					String column_title = column_rank.get(i).getTitle();
					int column_view = column_rank.get(i).getView_cnt();

				%>
					<tr class="bottom_border_td">
						<td class="music-rank"><%= i+1 %>
						</td>
						<td class="music-rank-object">
							<a href="${initParam.root }/wmplayer/columndetail.do?column_seq=<%= column_seq%>"><%=column_title %></a>
						</td>
						<td class="music-rank-writer"><%= column_view %>
						</td>
					</tr>
					<%} %>
			</table>
			<table class="board list" id="board-rank-list">
				<tr>
					<td colspan="3" class="bottom_border"><div class="board-rank-list-subject left-margin-subject ">인기 공유 게시글</div>
					</td>
				</tr>
				<%for( int i = 0 ; i < board_rank.size(); i++){
					int board_seq = board_rank.get(i).getBoard_seq();
					String board_title = board_rank.get(i).getBoard_title();
					String board_artist = board_rank.get(i).getBoard_artist();
					String board_userid = board_rank.get(i).getUserID();

				%>
					<tr>
						<td class="board-rank"><%= i+1 %>
						</td>
						<td class="board-rank-subject">
							<a href="${initParam.root }/wmplayer/sharedetail/view.do?board_seq=<%= board_seq%>"><%=board_title %>-<%=board_artist %></a>
						</td>
						<td class="board-rank-writer"><%= board_userid %>
						</td>
					</tr>
				<%} %>
			</table>
		</div>
