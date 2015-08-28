<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<script type="text/javascript" src="${initParam.root }/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
${alertMsg }
<form action="columnadd" method="POST" name="frm">
<div class="columnwrite-form">
	<div class="columnwrite-header">
		Column Editor
	</div>
	<div class="columnwrite-section">
		<table>
			<tr>
				<td class="columnwrite-title">제목</td>
				<td style="width: 370px;">
				<input name="title" type="text" placeholder="제목을 입력해주세요" size="50" maxlength="100"></td>
			</tr>
			<tr>
				<td class="columnwrite-title">해당 날씨</td>
				<td>
					<select name="mood">
						<option value="선택">=선택해주세요=</option>
						<option value="맑음">맑음</option>
						<option value="흐림">흐림</option>
						<option value="비">비</option>
						<option value="눈">눈</option>
						<option value="구름조금">구름조금</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnwrite-title">장르</td>
				<td><input name="style" type="text" size="50" maxlength="50"
				 placeholder="장르를 입력해주세요"></td>
			</tr>
			<tr>
				<td class="columnwrite-title">설명</td>
			<tr>
				<td colspan="2">
					<textarea name="contents" id="contents"cols="66" rows="20" 
					style="resize: none;" placeholder="설명을 입력해주세요"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="wm_btn">
		<!--  
		<input type="submit" class="styled-button-login" id="column" value="작 성" onclick="columnInput()"/>
		-->
		<input type="submit" class="styled-button-login" id="column"
		 value="작 성" onclick="submitContents(this)"/>
		<input type="button" class="styled-button-login" id="column"
		 value="취 소" onclick="location.href='column'"/>
	</div>
</div>
<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "contents",
    sSkinURI: "${initParam.root }/se2/SmartEditor2Skin.html",
    fCreator: "createSEditor2"
});


//‘저장’ 버튼을 누르는 등 저장을 위한 액션을 했을 때 submitContents가 호출된다고 가정한다.
function submitContents(elClickedObj) {
    // 에디터의 내용이 textarea에 적용된다.
    oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
 
    // 에디터의 내용에 대한 값 검증은 이곳에서
    // document.getElementById("ir1").value를 이용해서 처리한다.
 
    try {
        elClickedObj.submitForm.submit();
    
    } catch(e) {
    	
    }
}
</script>
</form>

