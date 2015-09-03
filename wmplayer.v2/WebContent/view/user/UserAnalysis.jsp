<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserAnalysis.css" rel="stylesheet" />
<link rel="stylesheet" href="${initParam.root }/css/style.css">
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
	 $("#shownamebt").click(function(){
		 var obj = $("#tdshowname");
		 if (obj.css("display") == "none"){
			 obj.show();
		 } else {
			 obj.hide();
			 nameAjax();
		 }
	 })
	 
	 $("#showpassbt").click(function(){
		var obj = $("#tdshowpass");
		if (obj.css("display") == "none"){
			obj.show();
		} else {
			obj.hide();
			passAjax();
		}
	})
	
	
	 $("#showbirthbt").click(function(){
		var obj = $("#tdbirthedit");
		if (obj.css("display") == "none"){
			obj.show();
		} else {
			obj.hide();
			birthAjax();
		}//else
	})
	
	 $("#showemailbt").click(function(){
		 var obj = $("#tdemailedit");
		 if (obj.css("display") == "none"){
			 obj.show();
		 } else {
			  obj.hide();
			  emailAjax();
		 }
	 })	
 	 
  });
	
 function passAjax(){
	 var passwd = $("input[name='passwd']").val();
	 var name = $("input[name='name']").val();
	 var year = $("select[name='year'] option:selected").val();
	 var month = $("select[name='month'] option:selected").val() - 1;
	 var day = $("select[name='date'] option:selected").val();
	 var birth = new Date(year, month, day);
	 var email = $("input[name='email_id']").val() +"@"+ $("select[name='email_addr'] option:selected").val();
	 
	 //alert(passwd +"|"+ name +"|"+ birth +"|"+ email);
	 $.ajax({
		 url:"update",
		 type:"POST",
		 data:{
			 passwd : passwd,
			 name : name,
			 birth : birth,
			 email : email
		 },
		 success:function(){
	         $('#editpasswd').html(passwd);
			 alert('패스워드가 수정되었습니다.');
		 }
	 });
	 
	 }
 function nameAjax(){
	 var passwd = $("input[name='passwd']").val();
	 var name = $("input[name='name']").val();
	 var year = $("select[name='year'] option:selected").val();
	 var month = $("select[name='month'] option:selected").val() - 1;
	 var day = $("select[name='date'] option:selected").val();
	 var birth = new Date(year, month, day);
	 var email = $("input[name='email_id']").val() +"@"+ $("select[name='email_addr'] option:selected").val();
	 $.ajax({
		 url:"update",
		 type:"POST",
		 data:{
			 passwd : passwd,
			 name : name,
			 birth : birth,
			 email : email
		 },
		 success:function(){
			 $('#editname').html(name);
			 alert('이름이 수정되었습니다.');
		 }
	 });
 }
    
 function birthAjax(){
	 var passwd = $("input[name='passwd']").val();
	 var name = $("input[name='name']").val();
	 var year = $("select[name='year'] option:selected").val();
	 var month = $("select[name='month'] option:selected").val() - 1;
	 var month2 = $("select[name='month'] option:selected").val();
	 var day = $("select[name='date'] option:selected").val();
	 var birth = new Date(year, month, day);
	 var email = $("input[name='email_id']").val() +"@"+ $("select[name='email_addr'] option:selected").val();
	 var birthday = year + "/"+month2+"/"+day;
	 $.ajax({
		 url:"update",
		 type:"POST",
		 data:{
			 passwd : passwd,
			 name : name,
			 birth : birth,
			 email : email
		 },
		 success:function(){
			 $('#editbirth').html(birthday);
			 alert('생년원일이 수정되었습니다.');
		 }
	 });
 }
 
 function emailAjax(){
	 var passwd = $("input[name='passwd']").val();
	 var name = $("input[name='name']").val();
	 var year = $("select[name='year'] option:selected").val();
	 var month = $("select[name='month'] option:selected").val() - 1;
	 var day = $("select[name='date'] option:selected").val();
	 var birth = new Date(year, month, day);
	 var email = $("input[name='email_id']").val() +"@"+ $("select[name='email_addr'] option:selected").val();
	 $.ajax({
		 url:"update",
		 type:"POST",
		 data:{
			 passwd : passwd,
			 name : name,
			 birth : birth,
			 email : email
		 },
		 success:function(){
			 $('#editemail').html(email);
			 alert('이메일이 수정되었습니다.');
		 }
	 });
 }
</script>
<div class="useranalysis-form">
	<div class="userinfo">
		<div class="avgbpm">
			<div class="title">
				<span>선호 음악 평균</span> <span class="sub-title">BPM</span>
			</div>
			<div class="average">
				<span>${avgbpm }</span>
			</div>
		</div>
		<div class="useranal-header">
			<table>
				<%-- /////////////////////////////////////비밀번호///////////////////////////////////////////// --%>
				<tr>
					<td>비밀번호</td>
					<td id="editpasswd"><c:forEach begin="1"
							end="${user.passwd.length()}">
				*
				</c:forEach></td>
					<!-- DB에 저장되어있는 비밀번호 길이 만큼 *로 표시한다 -->
					<td><input id="showpassbt"
								type="button" value="수정"></td>
				</tr>
				<tr>
					<td colspan="3" style="display: none" id="tdshowpass">
					<input id="passtext" type="password" name="passwd"
						value="${user.passwd }"></td>
				</tr>

				<%-- ////////////////////////////////////////이 름////////////////////////////////////////// --%>
				<tr>
					<td>이 름</td>
					<td id="editname">${user.name }</td>
					<td><input id="shownamebt" type="button" value="수정"></td>
				</tr>
				<tr>
					<td colspan="3" style="display: none" id="tdshowname">
						<input id="nametext" type="text" name="name" value="${user.name }">
					</td>
				</tr>

				<%-- ////////////////////////////////////////생년월일////////////////////////////////////////// --%>

				<c:set var="birth_dt" value="${fn:split(user.birth, '/') }" />
				<c:set var="year" value="${birth_dt[0] eq 'null'?'':birth_dt[0] }" />
				<c:set var="month" value="${birth_dt[1] eq 'null'?'':birth_dt[1] }" />
				<c:set var="date" value="${birth_dt[2] eq 'null'?'':birth_dt[2] }" />
				<tr>
					<td>생년월일</td>
					<td id="editbirth">${user.birth }</td>
					<!-- 버튼 클릭시 관련 select 창이 뜬다  -->
					<td>
					<input type="button" id="showbirthbt" value="수정"></td>
					<!-- introform.jsp 에 join 폼에 있는 형식을 참고 -->
				</tr>
				<tr>
					<td colspan="3" style="display: none" id="tdbirthedit"><select
						class="birth" name="year">
							<option value="">::년::</option>t
							<c:forEach var="i" begin="1920" end="2015" step="1">
								<option value="${i }" ${i eq year? 'selected' : '' }>${ i }</option>
							</c:forEach>
					</select> <select class="birth" name="month">
							<option value="">::월::</option>
							<c:forEach var="i" begin="1" end="12" step="1">
								<option value="${i<10?'0':'' }${i}"
									${i eq month? 'selected' : '' }>${i<10?'0':'' }${i}</option>
							</c:forEach>
					</select> <select class="birth" name="date">
							<option value="">::일::</option>
							<c:forEach var="i" begin="1" end="31" step="1">
								<option value="${i<10?'0':'' }${i }"
									${i eq date? 'selected' : '' }>${i<10?'0':'' }${i }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>

					<%-- ///////////////////////////////////////이메일/////////////////////////////////////////// --%>
					<td>이메일</td>
					<td id="editemail">${user.email }</td>
					<!-- 수정버튼 클릭시 관련 emailid 부분은  input text, 뒷부분은 select   -->
					<td><input id="showemailbt"
								type="button" value="수정"></td>
					<!-- introform.jsp 에 join 폼에 있는 형식을 참고 -->
				</tr>
				<tr>
					<td colspan="3" style="display: none" id="tdemailedit"><input
						type="text" name="email_id"
						value="${fn:split(user.email, '@')[0] }"> @ <select
						class="sel_email" name="email_addr">
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
			</table>
		</div>
	</div>
	<div class="useranal-section">
		<div class="anal-left">
			<table>
				<tr>
					<td colspan="2" id="anal-th"><span>당신의 </span> <span
						id="anal-like">좋아요</span> <span>곡 목록</span> <span id="anal-cnt">${musicsize }</span>
						<span>건</span></td>
				</tr>
				<c:forEach items="${music }" var="list" varStatus="status">
					<tr>
						<td class="like-rank">${status.count }</td>
						<td class="like-rank-object">${list.title }-${list.artist }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="anal-right">
			<table>
				<tr>
					<td colspan="3" id="anal-th"><span>당신이 올린 </span> <span
						id="anal-board">MusicVideo</span> <span id="anal-cnt">${listsize }
					</span> <span>건</span></td>
				</tr>
				<c:forEach items="${share }" var="list" varStatus="status">
					<tr>
						<td class="like-rank">${status.count }</td>
						<td class="like-rank-object"><a href="#"
							onclick="setLink(null, 'share', 'content', { 'board_seq' : ${list.board_seq}})">${list.board_title }-${list.board_artist }</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="anal-button">
		<input type="button" class="styled-button-login" id="drop"
			value="회원 탈퇴" onclick="location.href='dropform'" />
	</div>
</div>
