<%@ page language="java" contentType="text/html; charset=EUC-KR"
     pageEncoding="EUC-KR" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/TopMenu.css" rel="stylesheet" />
<table class="menu english">
	<tr>
		<td class="width top"><a href="homelist" target="section">
		<img id="logo" src="${ initParam.root}/img/button/logo_home.png"/></a></td>
		<td class="width top bottom"><a id="notice_top" href="notice" target="section">��������</a></td>
		<td class="width top bottom"> <a id="share_top" href="share" target="section">�����Խ���</a></td>
		<td class="width top bottom"> <a id="column_top" href="column" target="section">Į���Խ���</a></td>
		<td class="top">
			<span id="user_top">
				<c:choose>
					<c:when test="${success=='admin' }">
						<a id="join_top" href="manager" >������ ����</a>
					</c:when>
					<c:otherwise>
						<a id="join_top" href="userinfo" >�� ����</a>
					</c:otherwise>
				</c:choose>
				&nbsp;&nbsp; <span id="text_bar">|</span> 
				&nbsp;&nbsp; 
				<a id="logout_top" href="logout" target="_parent">�α׾ƿ�</a>
			</span>
		</td>
	</tr>
</table>
