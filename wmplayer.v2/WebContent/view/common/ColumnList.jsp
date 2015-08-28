<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnList.css"
	rel="stylesheet" />
<div class="column-list-form">
	<div class="column-list-header">
		<span>Column</span>
	</div>
	<div class="column-list-section">
		<table class="column-list-table">
			<tr>
				<th width="10%">번호</th>
				<th width="60%">제목</th>
				<th width="20%">일자</th>
				<th width="10%">조회수</th>
			</tr>
			<c:forEach items="${column }" var="column">
				<tr align="center">
					<td>${ column.column_seq }</td>
					<td><a href="columndetail?column_seq=${ column.column_seq }">${ column.title }</a></td>
					<td>${ column.update_day }</td>
					<td>${ column.view_cnt }</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${success=='admin' }">
		<input type="submit" class="styled-button-12" id="column"
			value="Write"
			onclick="location.href='columnform'" />
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
				<c:forEach begin="1" end="${endPage }" var="page">
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