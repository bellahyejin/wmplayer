<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserDrop.css" rel="stylesheet" />
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script src="${initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript">
    /* function DropUser(){
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

		if(dd==''){
	    	alert("회원님의 탈퇴할 이유를 선택해주세요.");

    	alert(id+'님이 탈퇴되었습니다.');
    	setLink("frm", "userdrop");
    	}
    } */

    $(document).ready(function()
    	{
    		$("#drop-radio :radio").change(function()
    			{
    				$("#etctext").prop("disabled", !$("#drop-radio :radio[value='5']").is(":checked"));
    			});

    		$(".drop").click(function()
    			{
    				if (confirm("정말로 탈퇴하시겠습니까?"))
    				{
    					$.ajax({
    							type : "post",
    							url : "${ initParam.root }/userdrop.ajax",
    							data : {
    								dropreason : $("#drop-radio :radio:checked").attr("value"),
    								etctext : $("#etctext").val()
    							},
    							success : function(data)
    							{
    								if (data == "success")
    								{
    									alert("정상적으로 탈퇴되었습니다");
    									parent.intro();
    								}
    								else alert("나중에 다시 해주시기 바랍니다");
    							},
    							error : function()
    							{
    								alert("error");
    							}
    						});
    				}
    			});

    		$(".undrop").click(function()
    			{
    				setLink(null, "userinfo");
    			});
    	});
</script>
<form name="frm" method="POST">
<div id="drop-form">
    <input type="hidden" value="${success }" name="id">
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
			<textarea id="etctext" rows="5" cols="66" disabled="disabled" maxlength="140" name="etctext"></textarea>
		</div>
	</div>
	<div class="drop-button">
				<input type="button" class="styled-button-login drop" id="drop" value="확인" />
				<input type="button" class="styled-button-login undrop" id="drop" value="취소" />
	</div>
</div>
</form>