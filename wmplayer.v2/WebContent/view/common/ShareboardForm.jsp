<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<link type="text/css" href="${ initParam.root }/css/global.css" rel="stylesheet" />
<link type="text/css" href="${ initParam.root }/css/ShareboardForm.css" rel="stylesheet" />
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript">
	if ("${ data.fail }" == "fail") alert("노래가 없습니다");
	else if ("${ data.fail }" == "error") alert("나중에 다시 시도해주세요");
	else if ("${ data.fail }" == "blank") alert("빈칸을 모두 채워주세요");
</script>
<form id="writeform" method="post">
	<div class="sharewrite-form">
		<div class="sharewrite-header">
			Share Music Video
		</div>
		<div class="sharewrite-contents">
			<table>
				<tr>
					<td id="title">곡 명</td>
					<td id="con"><input type="text" name="board_title" placeholder="  제목을 입력해주세요" /></td>
				</tr>
				<tr>
					<td id="title">아티스트</td>
					<td id="con"><input type="text" name="board_artist" placeholder="  아티스트를 입력해주세요" /></td>
				</tr>
				<tr>
					<td id="title">어울리는 날씨</td>
					<td id="con">
						<select name="weather_custom" id="weather-customer">
							<option value="">=선택해주세요=</option>
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
					<td colspan="2" id="con"><textarea name="board_desc" cols="50" rows="20" placeholder="  내용을 입력해주세요"></textarea></td>
				</tr>
			</table>
		</div>
		<div id="wm_btn">
			<input type="button" value="작 성" id="column" class="styled-button-login" onclick="setLink('#writeform', 'share', 'data_insert')" />
			<input type="button" value="취 소" id="column" class="styled-button-login" onclick="setLink(null, 'share', 'list')" />
		</div>
	</div>
</form>
<script type="text/javascript">
$(document).ready(function()
	{
		$(".sharewrite-contents :text[name='board_title']").val("${ data.bean.board_title }");
		$(".sharewrite-contents :text[name='board_artist']").val("${ data.bean.board_artist }");
		$(".sharewrite-contents option[value='${ data.bean.weather_custom }']").attr("selected", "selected");
		$(".sharewrite-contents textarea").html("${ data.bean.board_desc }");
	});
</script>