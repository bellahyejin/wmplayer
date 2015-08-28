<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			CanvasJS.addColorSet("gender_men", ["#3333ff", "#ffffff"]);
			CanvasJS.addColorSet("gender_women", ["#ffffff", "#ff3333"]);

			$("#chart1").CanvasJSChart({
				colorSet : "gender_men",
				width : 300,
				height : 250,
				title : {
					text : "회원가입 수(남자)"
				},
				axisY : {
					title : "y축"
				},
				data : [{
					type : "doughnut",
					indexLabel : "{ }",
					startAngle : 270,
					toolTipContent : "{ label } : { y }명",
					dataPoints : ${ gender_join_list }
				}]});

			$("#chart2").CanvasJSChart({
				colorSet : "gender_women",
				width : 300,
				height : 250,
				title : {
					text : "회원가입 수(여자)"
				},
				axisY : {
					title : "y축"
				},
				data : [{
					type : "doughnut",
					indexLabel : "{ }",
					startAngle : 270,
					toolTipContent : "{ label } : { y }명",
					dataPoints : ${ gender_join_list }
				}]});

			$("#chart3").CanvasJSChart({
				width : 600,
				height : 250,
				title : {
					text : "회원가입 수(나이별)"
				},
				axisX : {
					labelFormatter : function(data)
						{
							return "";
						}
				},
				axisY : {
					gridThickness : 0
				},
				data : ${ age_join_list }
				});
		});
	</script>
</head>
<body>
	<div class="manager-view">
		<div class="manager-menu">
			<a href="#" class="link" onclick="setLink(null, 'manager', 'userinfo')">회원 목록</a> |
			<a href="#" class="link" onclick="setLink(null, 'manager', 'dropreason')">탈퇴 이유</a> |
			 통계 현황[ <a href="#" class="link" onclick="setLink(null, 'manager', 'chartlogin')">로그인</a> | 회원가입 ]
		</div>
		<div id="chart1"></div>
		<div id="chart2"></div>
		<div id="chart3"></div>
	</div>
</body>
</html>