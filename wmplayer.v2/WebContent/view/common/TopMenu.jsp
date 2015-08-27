<%@ page language="java" contentType="text/html; charset=EUC-KR"
     pageEncoding="EUC-KR" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/TopMenu.css" rel="stylesheet" />
<table class="menu english">
	<tr>
		<td class="width top"><a href="home" target="section">
		<img id="logo" src="${ initParam.root}/img/button/logo_home.png"/></a></td>
		<td class="width top bottom"><a id="notice_top" href="notice" target="section">공지사항</a></td>
		<td class="width top bottom"> <a id="share_top" href="share" target="section">공유게시판</a></td>
		<td class="width top bottom"> <a id="column_top" href="column" target="section">칼럼게시판</a></td>
		<td class="top">
			<span id="user_top">
				<a id="join_top" href="userinfo" >내 정보</a>
				&nbsp;&nbsp; <span id="text_bar">|</span> 
				&nbsp;&nbsp; 
				<a id="logout_top" href="logout" target="_parent">로그아웃</a>
			</span>
		</td>
	</tr>
</table>
