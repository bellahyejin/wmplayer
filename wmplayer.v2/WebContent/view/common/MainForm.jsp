<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="UTF-8"%>
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
      	 <iframe name="nav" src="${initParam.root }/view/common/MusicPlayer.jsp" width="300px" height="677px" style="border: 0px"></iframe>
	  </div>
	  <div id="content">
	       <iframe name="section" src="${initParam.root }/contents.do" width="900px" height="677px" style="border: 0; overflow-x: hidden; overflow-y:auto;" scrolling="yes">
	       </iframe>
         	</div>
      	</div>
   </body>
</html>