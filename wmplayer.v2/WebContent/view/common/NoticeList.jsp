<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic"%>
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnList.css"
	rel="stylesheet" />
<%
	request.setCharacterEncoding("UTF-8");
	NoticeboardDAO dao = new NoticeboardDAO();
	int totalNumber = dao.dataSize();
	int page_max = 10;
	int endPage = totalNumber / page_max;
	int beginPage = 0;
	request.setAttribute("totalNumber", totalNumber);
	request.setAttribute("page_max", page_max);
	request.setAttribute("endPage", endPage);
	request.setAttribute("beginPage", beginPage);
	

	if (request.getParameter("i") == null) 
	{
		int i = 0;		
		List<NoticeBoardDTO> list = dao.selectAll((page_max) * i,page_max);		
		request.setAttribute("list", list);
	} 
	else 
	{
		int i = Integer.parseInt(request.getParameter("i"));		
		List<NoticeBoardDTO> list = dao.selectAll((page_max) * i,page_max);	
		request.setAttribute("list", list);
	}
%>

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
			<logic:notPresent scope="session"  name="success">
				<c:forEach items="${list }" var="notice">
					<tr align="center">
						<td>${ notice.notice_seq }</td>
						<td><a
							href="${initParam.root }/wmplayer/login.do" onclick="return confirm('읽기권한이 없습니다. 로그인해주세요')">${ notice.title }</a></td>
						<td>${ notice.update_day }</td>
						<td>${ notice.view_cnt }</td>

					</tr>
				</c:forEach>
			</logic:notPresent>
			<logic:present scope="session"  name="success">
			<c:forEach items="${list }" var="notice">
				<tr align="center">
					<td>${ notice.notice_seq }</td>
					<td><a
						href="${initParam.root }/wmplayer/noticedetail.do?notice_seq=${ notice.notice_seq }">${ notice.title }</a></td>
					<td>${ notice.update_day }</td>
					<td>${ notice.view_cnt }</td>

				</tr>
			</c:forEach>
			</logic:present>
		</table>
		<script type="text/javascript">
		function login(){
			if (confirm('쓰기 권한이 없습니다')){
				location.href='/WMPlayer_Pro/wmplayer/login.do';
			} else {
				return false;
			}
		}
		</script>
		<logic:notPresent scope="session" name="success">
			<input type="submit" class="styled-button-12" id="notice"
				value="Write" onclick="login()" /> 	
		</logic:notPresent>
		<logic:present scope="session" name="success">
			<input type="submit" class="styled-button-12" id="notice"
				value="Write"
				onclick="location.href='${initParam.root}/wmplayer/noticewrite.do'" />
		</logic:present>
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