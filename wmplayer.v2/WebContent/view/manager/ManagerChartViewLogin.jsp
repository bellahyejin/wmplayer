<%@page import="java.util.Map"%>
<%@page import="util.Time"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="util.ListMap"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link type="text/css" href="${initParam.root}/css/ManagerView.css"
	rel="stylesheet" />
<link type="text/css"
	href="${ initParam.root }/css/ManagerChartView.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="../../js/jquery.canvasjs.min.js"></script>
<script type="text/javascript">
			$(document).ready(function()
				{
					$("#chart4").CanvasJSChart({
						width : 600,
						height : 250,
						title : {
							text : "�α��� Ƚ��(1����)"
						},
						aixY : {
							includeZero : false
						},
						data : [{
							type : "column",
							indexLabel : "{ y }",
							toolTipContent : "{ label } : { y }��",
							dataPoints : ${ week_login_list }
						}]});

					$("#chart5").CanvasJSChart({
						width : 600,
						height : 250,
						title : {
							text : "�α��� Ƚ��(�ð���)"
						},
						aixY : {
							includeZero : false
						},
						data : [{
							type : "line",
							indexLabel : "{ y }",
							toolTipContent : "{ label } : { y }��",
							dataPoints : ${ hour_login_list }
						}]});
				});
			</script>
</head>
<body>
	<div class="manager-view">
		<div class="manager-menu">
			<a href="${ initParam.root }/wmplayer/manager.do">ȸ�� ���</a> | <a
				href="${ initParam.root }/wmplayer/manager/dropreason.do">Ż�� ����</a>
			| ��� ��Ȳ[ �α��� | <a
				href="${ initParam.root }/wmplayer/manager/chartJoin.do">ȸ������ </a>]
		</div>
		<div id="chart4"></div>
		<div id="chart5"></div>
	</div>
</body>
</html>