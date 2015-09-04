<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<%-- ServletConfig.getInitParameter("root") --%>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
</head>
<body style="overflow-y: hidden;">
	<div id="wrap">
		<div id="nav">
			<iframe name="nav" src="musicplayer" width="300px" height="677px" style="border: 0px"></iframe>
		</div>
		<div id="content">
			<iframe name="section" src="content" width="900px" height="677px" style="border: 0; overflow-x: hidden; overflow-y: auto;" scrolling="yes"> </iframe>
		</div>
	</div>
	<script src="${initParam.root }/js/jquery-2.1.4.js"></script>
	<script type="text/javascript">
		//data send
		var params = '';
		var first_flag = true;

		window.onload = function()
		{
			getLocation_cord();
		}
		/*현재 위치 찾기 */
		function getLocation_cord()
		{
			if (navigator.geolocation)
			{
				navigator.geolocation.getCurrentPosition(showPosition);
			}
			else
			{
				$("#location")
						.html("Geolocation is not supported by this browser.");
			}
		}

		function showPosition(position)
		{
			var cur_Latitude = Math.floor(position.coords.latitude * 100) / 100; //위도
			var cur_Longitude = Math.floor(position.coords.longitude * 100) / 100; //경도
			params += 'cur_Latitude=' + cur_Latitude + '&cur_Longitude=' + cur_Longitude;
			$.ajax(
			{
			url : 'ajax/receiveData',
			type : 'POST',
			data : params,
			cache : false,
			success : function(doc)
			{
				if (first_flag)
				{
					nav.printData(doc);
					first_flag = false;
				}
				section.loaded(doc);
				params = "";
			}});
		}

		function intro()
		{
			location.href = "${ initParam.root }/intro";
		}
	</script>
</body>
</html>