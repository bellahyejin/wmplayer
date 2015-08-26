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
		<script type="text/javascript" src="../../js/jquery-2.1.4.js"></script>
	</head>
	<body>
	<div class="manager-view">
		<div class="manager-menu">
		<a href="${ initParam.root }/wmplayer/manager.do">회원 목록</a> | 탈퇴 이유 | 통계 현황[ <a href="${ initParam.root }/wmplayer/manager/chartLogin.do">로그인</a> | <a href="${ initParam.root }/wmplayer/manager/chartJoin.do">회원가입 </a>]
		</div>
			<div class="drop-reason-table">
			<div class="member-subject">탈퇴 이유</div>
			<table class="board">
				<tr>
					<th width="10%">번호</th>
					<th width="70%">이 유</th>
					<th width="20%">선택횟수</th>
				</tr>
				<c:forEach items="${ drop_reason_list.keySet() }" var="key"
					varStatus="sta">
					<tr align="center">
						<td>${ sta.count }</td>
						<td>${ key }</td>
						<td>${ drop_reason_list.get(key) }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	</body>
</html>