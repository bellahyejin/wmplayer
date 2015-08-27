<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnList.css"
	rel="stylesheet" />
<div class="column-list-form">
	<div class="column-list-header">
		<span>Notice</span>
	</div>
	<div class="column-list-section">
		<table class="column-list-table">
			<tr>
				<th width="10%">번호</th>
				<th width="60%">제목</th>
				<th width="20%">일자</th>
				<th width="10%">조회수</th>
			</tr>
			<c:forEach items="${noticelist }" var="notice">
				<tr align="center">
					<td>${ notice.notice_seq }</td>
					<td><a
						href="noticedetail?notice_seq=${ notice.notice_seq }">${ notice.title }</a></td>
					<td>${ notice.update_day }</td>
					<td>${ notice.view_cnt }</td>

				</tr>
			</c:forEach>
		</table>
		<c:if test="${success == 'admin' }">
			<input type="submit" class="styled-button-12" id="notice" value="Write" onclick="location.href='noticewrite'" />
		</c:if>
	</div>
	<div class="pager-container-column">
		<ul class="page-list">
			<div id="first">
				<c:choose>
					<c:when test="${ i == 0 }">
						<li class="none_a">start</li>
					</c:when>
					<c:otherwise>
						<li class="page" id="first-page"><a href="?i=0">start</a></li>
					</c:otherwise>
				</c:choose>
			</div>
			<div id="middle">
				<c:forEach begin="${beginPage }" end="${endPage }" var="page">
					<c:choose>
						<c:when test="${ i != page }">
							<li class="page"><a href="?i=${ page }">${ page }</a></li>
						</c:when>
						<c:otherwise>
							<li class="none_a">${ page }</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div id="end">
				<c:choose>
					<c:when test="${ i == endPage }">
						<li class="none_a">end</li>
					</c:when>
					<c:otherwise>
						<li class="page" id="end-page"><a href="?i=${ endPage }">end</a></li>
					</c:otherwise>
				</c:choose>
			</div>
		</ul>
	</div>
</div>