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
    	alert(id+'���� Ż��Ǿ����ϴ�.');
    	location.replace("http://localhost:2539/WMPlayer_Pro/wmplayer/drop/retire.do?dropreason="+dd+'&etctext='+text);

    	if(dd==''){
    	alert("ȸ������ Ż���� ������ �������ּ���.");
    	}
    }


</script>
<form name="frm" method="POST">
<div id="drop-form">
    <input type="hidden" value="<%=session.getAttribute("success")%>" name="id">
	<div class="drop-header">
		<div id="drop-text">WMPlayer�� <br>�̿��� �ּż� �����մϴ�</div>
		<div id="drop-logo"><img src="${initParam.root }/img/button/logo.png"></div>
	</div>
	<div class="drop-section">
		WMPlayer�� Ż�� �Ͻô� ������ �˷� �ֽø� ������ �ݿ� �� �� ���� ���񽺷� ã�� �˰ڽ��ϴ�.
	</div>
	<div class="drop-select">
		<div id="drop-image"></div>
		<div id="drop-select-cont">�Ʒ� �׸񿡼� WMplayer�� Ż���ϴ� ������ �����Ͽ��ּ���.</div>
		<div id="drop-radio">
			<input type="radio" name="dropreason" value="1"> ����� �����ؼ�<br>
			<input type="radio" name="dropreason" value="2"> �ٸ� ����Ʈ�� ����ؼ�<br>
			<input type="radio" name="dropreason" value="3"> ��õ ���� ������ �ȵ�<br>
			<input type="radio" name="dropreason" value="4"> ���� �ٿ�Ǽ�<br>
			<input type="radio" name="dropreason" value="5" onclick="DropUser()"> ��Ÿ<br>
			<textarea rows="5" cols="66" disabled="disabled" maxlength="140" name="etctext"></textarea>
		</div>
	</div>
	<div class="drop-button">
				<input type="button" class="styled-button-login" id="drop" value="Ȯ��" onclick="CheckReason()" />
				<input type="button" class="styled-button-login" id="drop" value="���" onclick="location.href='${initParam.root}/wmplayer/userinfo.do'"/>
	</div>
</div>
</form>