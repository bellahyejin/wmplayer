<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/TopMenu.css" rel="stylesheet" />
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<table class="menu english">
	<tr>
		<td class="width top"><a href="homelist" target="section">
		<img id="logo" src="${ initParam.root}/img/button/logo_home.png"/></a></td>
		<td class="width top bottom"><a id="notice_top" href="notice" target="section">공지사항</a></td>
		<td class="width top bottom"> <a id="share_top" href="#" target="section" onclick="setLink(null, 'share')">공유게시판</a></td>
		<td class="width top bottom"> <a id="column_top" href="column" target="section">칼럼게시판</a></td>
		<td class="top">
			<span id="user_top">
				<c:choose>
					<c:when test="${success=='admin' }">
						<a id="join_top" href="#" onclick="setLink(null, 'manager')" >관리자 정보</a>
					</c:when>
					<c:otherwise>
						<a id="join_top" href="userinfo" >내 정보</a>
					</c:otherwise>
				</c:choose>
				&nbsp;&nbsp; <span id="text_bar">|</span>
				&nbsp;&nbsp;
				<a id="logout_top" href="logout" target="_parent">로그아웃</a>
			</span>
		</td>
	</tr>
</table>
