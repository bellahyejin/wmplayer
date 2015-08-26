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
				<td id="title">곡 명</td>
				<td id="con">
					<input type="text" name="musictitle" placeholder="  제목을 입력해주세요" >
				</td>
			</tr>
			<tr>
				<td id="title">아티스트</td>
				<td id="con">
					<input type="text" name="artist" placeholder="  아티스트를 입력해주세요">
				</td>
			</tr>
			<tr>
				<td id="title">어울리는 날씨</td>
				<td id="con">
					<select id="weather-customer" name="weather">
						<option>=선택해주세요=</option>
						<option value="맑음">맑음</option>
						<option value="흐림">흐림</option>
						<option value="비">비</option>
						<option value="눈">눈</option>
						<option value="바람">바람</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" id="title">음악에 대한 설명</td>
			</tr>
			<tr>
				<td colspan="2" id="con">
					<textarea name="contents" rows="20" cols="50" placeholder="  내용을 입력해주세요"></textarea>
				</td>
			</tr>
			
		</table>
	</div>
	<div id="wm_btn">
		<input type="submit" class="styled-button-login" id="column" value="작 성" />
		<input type="button" class="styled-button-login" id="column" value="취 소" onclick="location.href='${initParam.root}/wmplayer/sharelist.do'"/>
	</div>
</div>
</form>
