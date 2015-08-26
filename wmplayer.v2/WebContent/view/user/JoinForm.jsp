<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<head>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="../js/ajax.js"></script>
<script src="../js/StringConstructor.js"></script>

<link type="text/css" href="../css/global.css" rel="stylesheet" />
<link type="text/css" href="../css/JoinForm.css" rel="stylesheet" />
<script type="text/javascript">
	function duplicate() {
		var id_textfield = document.frm.writeid;
		var id = id_textfield.value;
		alert(id);
		document.getElementById("check").innerHTML = "";
		document.getElementById("idwrite").innerHTML = "";
        
		if (id.length == 0) {
			document.getElementById("idwrite").innerHTML = "아이디를 입력해주세요";
		} else {
			var idRegx = /[\d|a-z|A-Z]{8,16}/gi;
			if (idRegx.test(id)) {
				sendRequest('idcheck.jsp', "writeid=" + id, callback, 'POST');
			} else {
				document.getElementById("check").innerHTML = "아이디는 영문, 숫자 및 8자이상 16자이하만 입력가능합니다";
			}
		}
		
		id_textfield.focus();
	}

	function callback() {
		if (xhr.readyState == 4 && xhr.status == 200) {
				var idMsg = xhr.responseText.trim();
				alert(idMsg);
				if (idMsg == "enable")
				{
					document.getElementById("idwrite").innerHTML = "사용 가능한 아이디 입니다";
					document.frm.pass.focus();
				}
				else
					document.getElementById("idwrite").innerHTML = "사용 중인 아이디 입니다";
			}
	}

	function checkid() {
		var id = document.getElementById("writeid");
		var pass = document.getElementById("pass");
	}
</script>
<script type="text/javascript">
$(function(){
	$("#pass, #passcheck").keyup(function(){
		var pass = $("#pass").val();
		var passcheck = $("#passcheck").val();
		var message = checkPass(pass);
		var message2 = checkPassRe(pass, passcheck)
		$("#passmsg").text(message);
		$("#message1").text(message2)
	});
})
function checkPass(val){
	var passRegx = /^[\d|a-z|A-Z|]{10,20}$/gi;
	var message;
	if (val == '') {
		message = ""
	} else if (passRegx.test(val)){
		message = "";
	} else {
		message = "비밀번호는 영문 대소문자와 숫자,특수문자를 포함 10자 이상이여야 합니다.";
	}
	return message;
}
function checkPassRe(pass, passcheck){
	var message;
	if (passcheck == '') {
		message = '';
	} else {
		if (pass == '') {
			message = '비밀번호를 입력해 주세요';
		} else if (pass == passcheck) {
			message = '';
		} else {
			message = '비밀번호 확인이 일치하지 않습니다';
		}
	}
	return message;
}
</script>
<!-- javascript -->
</head>
<form name="frm" method="GET" id="join-form"
	action="${initParam.root }/wmplayer/join/insert.do">
	<legend>Join</legend>
	<br>
	<table >
		<tr>
			<td class="title_join">I D *</td>
			<td class="con_join"><input class="in_text" type="text"
				name="writeid">&nbsp; <input type="button"
				class="styled-button-login" id="join" name="idcheck" value="중복확인"
				onclick="duplicate()" />
				<span id="check"></span><br>
				<div id="idwrite"></div> <span id="msg1"></span><span id="msg2"></span><span
				id="msg3"></span>
			</td>
			<td>
				
		   </td>
		</tr>
		<tr>
			<td class="title_join">비밀번호 *</td>
			<td class="con_join"><input class="in_text" type="password"
				name="pass" id="pass" maxlength="20"><div id="passmsg"></div>
				</td>
				
		</tr>
		<tr>
			<td class="title_join">비밀번호 재확인 *</td>
			<td class="con_join"><input class="in_text" type="password"
				name="passcheck" id="passcheck">&nbsp;<span
				id="message1" maxlength="20"></span><span id="message2"></span></td>
		</tr>
		<tr>
			<td class="title_join">이 름 *</td>
			<td class="con_join"><input class="in_text" type="text"
				name="name"></td>
		</tr>
		<tr>
			<td class="title_join">생 년 월 일 *</td>
			<td class="con_join"><select class="birth" name="year">
					<%
						for (int i = 20; i <= 115; i++) {
							int year = 1900 + i;
							out.print("<option>" + year + "</option>");
						}
					%>
			</select> <select class="birth" name="month">
					<%
						for (int i = 1; i <= 12; i++) {
							String month = (i < 10 ? "0" : "") + i;
							out.print("<option value=" + month + ">" + (i < 10 ? "0" : "")
									+ i + "</option>");
						}
					%>
			</select> <select class="birth" name="date">
					<%
						for (int i = 1; i <= 31; i++) {
							String date = (i < 10 ? "0" : "") + i;
							out.print("<option value=" + date + ">" + (i < 10 ? "0" : "")
									+ i + "</option>");
						}
					%>
			</select></td>
		</tr>
		<tr>
			<td class="title_join">성 별 *</td>
			<td class="con_join"><input type="radio" name="gender" value="남">&nbsp;남&nbsp;
				<input type="radio" name="gender" value="여">&nbsp;여</td>
		</tr>
		<tr>
			<td class="title_join">E-mail</td>
			<td class="con_join"><input class="email_id" type="text"
				name="email_id"> @ <select class="sel_email"
				name="email_addr">
					<option>naver.com</option>
					<option>freechal.com</option>
					<option>dreamwiz.com</option>
					<option>korea.com</option>
					<option>lycos.co.kr</option>
					<option>yahoo.co.kr</option>
					<option>hanmail.net</option>
					<option>gmail.com</option>
					<option>paran.com</option>
					<option>hotmail.com</option>
					<option>nate.com</option>
					<option>직접입력</option>
			</select></td>
		</tr>
		<tr>
			<td class="join_submit" colspan="2" align="center"><input
				type="submit" class="styled-button-login" id="join" value="회원가입" />
				<input type="button" class="styled-button-login" id="join"
				value="취소"
				onclick="location.href='${initParam.root}/wmplayer/home.do'" /></td>
		</tr>
	</table>
</form>
