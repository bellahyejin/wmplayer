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
			document.getElementById("idwrite").innerHTML = "���̵� �Է����ּ���";
		} else {
			var idRegx = /[\d|a-z|A-Z]{8,16}/gi;
			if (idRegx.test(id)) {
				sendRequest('idcheck.jsp', "writeid=" + id, callback, 'POST');
			} else {
				document.getElementById("check").innerHTML = "���̵�� ����, ���� �� 8���̻� 16�����ϸ� �Է°����մϴ�";
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
					document.getElementById("idwrite").innerHTML = "��� ������ ���̵� �Դϴ�";
					document.frm.pass.focus();
				}
				else
					document.getElementById("idwrite").innerHTML = "��� ���� ���̵� �Դϴ�";
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
		message = "��й�ȣ�� ���� ��ҹ��ڿ� ����,Ư�����ڸ� ���� 10�� �̻��̿��� �մϴ�.";
	}
	return message;
}
function checkPassRe(pass, passcheck){
	var message;
	if (passcheck == '') {
		message = '';
	} else {
		if (pass == '') {
			message = '��й�ȣ�� �Է��� �ּ���';
		} else if (pass == passcheck) {
			message = '';
		} else {
			message = '��й�ȣ Ȯ���� ��ġ���� �ʽ��ϴ�';
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
				class="styled-button-login" id="join" name="idcheck" value="�ߺ�Ȯ��"
				onclick="duplicate()" />
				<span id="check"></span><br>
				<div id="idwrite"></div> <span id="msg1"></span><span id="msg2"></span><span
				id="msg3"></span>
			</td>
			<td>
				
		   </td>
		</tr>
		<tr>
			<td class="title_join">��й�ȣ *</td>
			<td class="con_join"><input class="in_text" type="password"
				name="pass" id="pass" maxlength="20"><div id="passmsg"></div>
				</td>
				
		</tr>
		<tr>
			<td class="title_join">��й�ȣ ��Ȯ�� *</td>
			<td class="con_join"><input class="in_text" type="password"
				name="passcheck" id="passcheck">&nbsp;<span
				id="message1" maxlength="20"></span><span id="message2"></span></td>
		</tr>
		<tr>
			<td class="title_join">�� �� *</td>
			<td class="con_join"><input class="in_text" type="text"
				name="name"></td>
		</tr>
		<tr>
			<td class="title_join">�� �� �� �� *</td>
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
			<td class="title_join">�� �� *</td>
			<td class="con_join"><input type="radio" name="gender" value="��">&nbsp;��&nbsp;
				<input type="radio" name="gender" value="��">&nbsp;��</td>
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
					<option>�����Է�</option>
			</select></td>
		</tr>
		<tr>
			<td class="join_submit" colspan="2" align="center"><input
				type="submit" class="styled-button-login" id="join" value="ȸ������" />
				<input type="button" class="styled-button-login" id="join"
				value="���"
				onclick="location.href='${initParam.root}/wmplayer/home.do'" /></td>
		</tr>
	</table>
</form>
