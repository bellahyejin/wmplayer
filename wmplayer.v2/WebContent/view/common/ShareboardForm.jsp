<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ShareboardForm.css" rel="stylesheet" />
<form action="shareinput.do" method="post">
<div class="sharewrite-form">
	<div class="sharewrite-header">
		Share Music Video
	</div>
	<div class="sharewrite-contents">
		<table>
			<tr>
				<td id="title">�� ��</td>
				<td id="con">
					<input type="text" name="musictitle" placeholder="  ������ �Է����ּ���" >
				</td>
			</tr>
			<tr>
				<td id="title">��Ƽ��Ʈ</td>
				<td id="con">
					<input type="text" name="artist" placeholder="  ��Ƽ��Ʈ�� �Է����ּ���">
				</td>
			</tr>
			<tr>
				<td id="title">��︮�� ����</td>
				<td id="con">
					<select id="weather-customer" name="weather">
						<option>=�������ּ���=</option>
						<option value="����">����</option>
						<option value="�帲">�帲</option>
						<option value="��">��</option>
						<option value="��">��</option>
						<option value="�ٶ�">�ٶ�</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" id="title">���ǿ� ���� ����</td>
			</tr>
			<tr>
				<td colspan="2" id="con">
					<textarea name="contents" rows="20" cols="50" placeholder="  ������ �Է����ּ���"></textarea>
				</td>
			</tr>
			
		</table>
	</div>
	<div id="wm_btn">
		<input type="submit" class="styled-button-login" id="column" value="�� ��" />
		<input type="button" class="styled-button-login" id="column" value="�� ��" onclick="location.href='${initParam.root}/wmplayer/sharelist.do'"/>
	</div>
</div>
</form>
