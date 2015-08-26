<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>Insert title here</title>
		<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
		<link type="text/css" href="${initParam.root}/css/ManagerView.css" rel="stylesheet" />
		<script type="text/javascript" src="${initParam.root}/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
		<script type="text/javascript">
			$(document).ready(function()
				{
					$(".drop-etc-table").hide(0);
					$("#etc-reason").hover(function()
						{
							$(this).css("color", "#81d4fa");
							$(this).css("cursor", "pointer");
						}, function()
						{
							$(this).css("color", "#000000");
							$(this).css("cursor", "none");
						});
					$("#etc-reason").on("click", function()
						{
							$(this).css("color", "#62c8f9");
							$(".drop-etc-table").toggle("fast");
						});
				});
		</script>
	</head>
	<body>
	<div class="manager-view">
		<div class="manager-menu">
			<a href="#" class="link" onclick="setLink(null, 'manager', 'userinfo')">회원 목록</a> |
			탈퇴 이유 |
			통계 현황[ <a href="#" class="link" onclick="setLink(null, 'manager', 'chartlogin')">로그인</a> |
		<a href="#" class="link" onclick="setLink(null, 'manager', 'chartjoin')">회원가입</a> ]
		</div>
		<div class="drop-reason-table">
			<div class="member-subject">탈퇴 이유</div>
			<table class="board">
				<tr>
					<th width="10%">번호</th>
					<th width="70%">이 유</th>
					<th width="20%">선택횟수</th>
				</tr>
				<c:forEach items="${ drop_reason_list }" var="reason"
					varStatus="sta">
					<tr align="center">
						<td>${ sta.count }</td>
						<td><c:choose>
							<c:when test="${ sta.last }"><span id="etc-reason">${ reason.reason }</span></c:when>
							<c:otherwise>${ reason.reason }</c:otherwise>
						</c:choose></td>
						<td>${ reason.count_reason }
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="drop-etc-table">
			<div class="member-subject"></div>
			<table class="board">
				<tr>
					<th width="70%">이 유</th>
					<th width="30%">선택횟수</th>
				</tr>
				<c:forEach items="${ drop_etc_list }" var="etc">
					<tr align="center">
						<td>${ etc.reason }</td>
						<td>${ etc.count_reason }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	</body>
</html>