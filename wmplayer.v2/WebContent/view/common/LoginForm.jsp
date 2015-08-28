<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/LoginForm.css" rel="stylesheet" />
<script type="text/javascript">
window.onload=function(){
	document.frm.userid.value='';
	document.frm.userpass.value='';

}
</script>
<html:messages id="msg" message="true">
   <script type="text/javascript">alert('<bean:write name="msg" />');</script>
</html:messages>
<form name='frm' action="${initParam.root }/wmplayer/login/success.do" method="POST">
<div id="form-position">
	<div id="loginform">
		<div class="login-header">login</div>
		<br>
		<br>
		<table>
			<tr class="login_id">
				<td class="login_title">I D</td>
				<td><input class="text" type="text" name="userid"></td>
			</tr>
			<tr class="login_pass">
				<td class="login_title">Password</td>
				<td><input class="text" type="password" name="userpass"></td>
			</tr>
			<tr>
				<td colspan="2" align="center" id="bt_row">
					<input type="submit" class="styled-button-login" id="logina" value="로그인" >
					<input type="button" class="styled-button-login" id="join" value="회원가입" onclick="location.href='${initParam.root}/wmplayer/join.do'"/>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</html>