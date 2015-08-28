<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${initParam.root }/css/style_homeform.css"/>
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="jquery-script-clear"></div>
    <div class="banner">
        <section id="dg-container" class="dg-container">
  <div class="dg-wrapper">
  	<%
		ShareboardDAO dao = new ShareboardDAO();
		List<BoardUserDTO> board_rank = dao.selectPop();	
  		
		for( int i = 0 ; i < board_rank.size(); i++){ 
			int board_seq = board_rank.get(i).getBoard_seq();
			String board_title = board_rank.get(i).getBoard_title();
			String board_artist = board_rank.get(i).getBoard_artist();
			String board_userid = board_rank.get(i).getUserID();
			String board_albumcover = board_rank.get(i).getAlbumcover();
  	%>
  		<a href="#">
          <img src="<%= board_albumcover%>" width="300px" height="300px"> 
      	</a>
    <%} %>
  </div>
  <ol class="button" id="lightButton">
      <li index="0">
      <li index="1">
      <li index="2">
      <li index="3">
      <li index="4">
      <li index="5">
      <li index="6">
      <li index="7">
      <li index="8">
      <li index="9">
  </ol>
   <%= board_rank.size() %>
  <nav>
      <span class="dg-prev"></span>
      <span class="dg-next"></span>
  </nav>
</section>
    </div>
<script src="${initParam.root }/js/modernizr.custom.53451.js"></script>
<script src="${initParam.root }/js/jquery-2.1.4.js"></script>
    <script src="${initParam.root }/js/carrousel.min.js"></script>
    <script >
        $(function () {
            $('#dg-container').carrousel({
                current: 0,
                autoplay: true,
                interval: 3000
            });
        });
    </script>
    <script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</body>
</html>