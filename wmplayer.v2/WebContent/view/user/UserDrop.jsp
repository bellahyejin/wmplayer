<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserDrop.css" rel="stylesheet" />
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<script type="text/javascript">
    function DropUser(){
    		  alert(document.frm.dropreason.value);
    	  if(document.frm.dropreason.value=='5'){
    	  document.frm.etctext.disabled=false;
    	  }else{
    		  document.frm.etctext.disabled='disabled';
    	  }
    }

    function CheckReason(){
    	var dd = document.frm.dropreason.value;
    	var text = document.frm.etctext.value;
		var id = document.frm.id.value;
    	alert(id+'님이 탈퇴되었습니다.');
    	location.replace("http://localhost:2539/WMPlayer_Pro/wmplayer/drop/retire.do?dropreason="+dd+'&etctext='+text);

    	if(dd==''){
    	alert("회원님의 탈퇴할 이유를 선택해주세요.");
    	}
    }


</script>
<form name="frm" method="POST">
<div id="drop-form">
    <input type="hidden" value="<%=session.getAttribute("success")%>" name="id">
	<div class="drop-header">
		<div id="drop-text">WMPlayer를 <br>이용해 주셔서 감사합니다</div>
		<div id="drop-logo"><img src="${initParam.root }/img/button/logo.png"></div>
	</div>
	<div class="drop-section">
		WMPlayer를 탈퇴 하시는 이유를 알려 주시면 개선에 반영 해 더 좋은 서비스로 찾아 뵙겠습니다.
	</div>
	<div class="drop-select">
		<div id="drop-image"></div>
		<div id="drop-select-cont">아래 항목에서 WMplayer를 탈퇴하는 이유를 선택하여주세요.</div>
		<div id="drop-radio">
			<input type="radio" name="dropreason" value="1"> 기능이 부족해서<br>
			<input type="radio" name="dropreason" value="2"> 다른 사이트를 사용해서<br>
			<input type="radio" name="dropreason" value="3"> 추천 곡이 마음에 안들어서<br>
			<input type="radio" name="dropreason" value="4"> 자주 다운되서<br>
			<input type="radio" name="dropreason" value="5" onclick="DropUser()"> 기타<br>
			<textarea rows="5" cols="66" disabled="disabled" maxlength="140" name="etctext"></textarea>
		</div>
	</div>
	<div class="drop-button">
				<input type="button" class="styled-button-login" id="drop" value="확인" onclick="CheckReason()" />
				<input type="button" class="styled-button-login" id="drop" value="취소" onclick="location.href='${initParam.root}/wmplayer/userinfo.do'"/>
	</div>
</div>
</form>