<%@ page language="java" contentType="text/html; charset=EUC-KR"
     pageEncoding="EUC-KR" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/TopMenu.css" rel="stylesheet" />
<table class="menu english">
	<tr>
		<td class="width top"><a href="${initParam.root }/wmplayer/home.do" target="section">
		<img id="logo" src="${ initParam.root}/img/button/logo_home.png"/></a></td>
		<td class="width top bottom"><a id="notice_top" href="${initParam.root}/wmplayer/noticelist.do" target="section">NOTICE</a></td>
		<td class="width top bottom"> <a id="share_top" href="${initParam.root}/wmplayer/sharelist.do" target="section">SHARE</a></td>
		<td class="width top bottom"> <a id="column_top" href="${initParam.root}/wmplayer/columnlist.do" target="section">COLUMN</a></td>
		<td class="top"><span id="user_top">
             <logic:present scope="session" name="success" >
				<a id="logout_top" href="${initParam.root }/wmplayer/logout/success.do" target="_parent">LOGOUT</a>
				&nbsp;&nbsp; <span id="text_bar">|</span> 
				&nbsp;&nbsp; <a id="join_top" href="#" onclick="alert('회원님께선 이미 회원가입을 하셨습니다!!'); return false;">JOIN</a>
			</logic:present>
			<logic:notPresent scope="session" name="success">
				<a id="login_top" href="${initParam.root}/wmplayer/login.do" target="section">LOGIN</a>
				&nbsp;&nbsp; <span id="text_bar">|</span> &nbsp;&nbsp; <a id="join_top" href="${initParam.root }/wmplayer/join.do" >JOIN</a>
	        </logic:notPresent>
		</span></td>
	
	</tr>
</table>
