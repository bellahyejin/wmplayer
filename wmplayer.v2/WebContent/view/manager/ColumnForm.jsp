<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
	 <%request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<script type="text/javascript" src="${initParam.root }/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<form action="${initParam.root }/wmplayer/columnform/add.do" method="POST" name="frm">
<div class="columnwrite-form">
	<div class="columnwrite-header">
		Column Editor
	</div>
	<div class="columnwrite-section">
		<table>
			<tr>
				<td class="columnwrite-title">����</td>
				<td style="width: 370px;">
				<input name="title" type="text" placeholder="������ �Է����ּ���" size="50" maxlength="100"></td>
			</tr>
			<tr>
				<td class="columnwrite-title">�ش� ����</td>
				<td>
					<select name="mood">
						<option value="����">=�������ּ���=</option>
						<option value="����">����</option>
						<option value="�帲">�帲</option>
						<option value="��">��</option>
						<option value="��">��</option>
						<option value="��������">��������</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnwrite-title">�帣</td>
				<td><input name="style" type="text" size="50" maxlength="50"
				 placeholder="�帣�� �Է����ּ���"></td>
			</tr>
			<tr>
				<td class="columnwrite-title">����</td>
			<tr>
				<td colspan="2">
					<textarea name="contents" id="contents"cols="66" rows="20" 
					style="resize: none;" placeholder="������ �Է����ּ���"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="wm_btn">
		<!--  
		<input type="submit" class="styled-button-login" id="column" value="�� ��" onclick="columnInput()"/>
		-->
		<input type="submit" class="styled-button-login" id="column"
		 value="�� ��" onclick="submitContents(this)"/>
		<input type="button" class="styled-button-login" id="column"
		 value="�� ��" onclick="location.href='${initParam.root}/wmplayer/columnlist.do'"/>
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


//�����塯 ��ư�� ������ �� ������ ���� �׼��� ���� �� submitContents�� ȣ��ȴٰ� �����Ѵ�.
function submitContents(elClickedObj) {
    // �������� ������ textarea�� ����ȴ�.
    oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
 
    // �������� ���뿡 ���� �� ������ �̰�����
    // document.getElementById("ir1").value�� �̿��ؼ� ó���Ѵ�.
 
    try {
        elClickedObj.submitForm.submit();
    
    } catch(e) {
    	
    }
}
</script>
</form>

