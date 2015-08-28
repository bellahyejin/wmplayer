<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ManagerView.css"
	rel="stylesheet" />
<link type="text/css"
	href="${ initParam.root }/css/ManagerChartView.css" rel="stylesheet" />
<script type="text/javascript" src="${initParam.root}/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/jquery.canvasjs.min.js"></script>
<script type="text/javascript">
			$(document).ready(function()
				{
					$("#chart4").CanvasJSChart({
						width : 600,
						height : 250,
						title : {
							text : "로그인 횟수(1주일)"
						},
						aixY : {
							includeZero : false
						},
						data : [{
							type : "column",
							indexLabel : "{ y }",
							toolTipContent : "{ label } : { y }번",
							dataPoints : ${ week_login_list }
						}]});

					$("#chart5").CanvasJSChart({
						width : 600,
						height : 250,
						title : {
							text : "로그인 횟수(시간별)"
						},
						aixY : {
							includeZero : false
						},
						data : [{
							type : "line",
							indexLabel : "{ y }",
							toolTipContent : "{ label } : { y }번",
							dataPoints : ${ hour_login_list }
						}]});
				});
			</script>
</head>
<body>
	<div class="manager-view">
		<div class="manager-menu">
			<a href="#" class="link" onclick="setLink(null, 'manager', 'userinfo')">회원 목록</a> |
			<a href="#" class="link" onclick="setLink(null, 'manager', 'dropreason')">탈퇴 이유</a> |
			 통계 현황[ 로그인 |
		<a href="#" class="link" onclick="setLink(null, 'manager', 'chartjoin')">회원가입</a> ]
		</div>
		<div id="chart4"></div>
		<div id="chart5"></div>
	</div>
</body>
</html>