<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<link type="text/css" href="${ initParam.root }/css/global.css" rel="stylesheet" />
<link type="text/css" href="${ initParam.root }/css/ShareboardDetail.css" rel="stylesheet" />
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script type="text/javascript">
	function warning(str)
	{
		alert(str + "페이지가 없습니다");
	}

	this.board_seq = ${ data.board_seq };

	function getData(page)
	{
		this.data = {
				board_seq : ${ data.board_seq },
				page_no : page
			};

		if(!JSONDataCompare(this.prev_data, this.data))
		{
			this.prev_data = this.data;
			$.ajax({
				type : "post",
				url : "${ initParam.root }/share/reple",
				dataType : "html",
				data : this.data,
				success : function(data, status, xhr)
				{
					$("#appendreple").html(data);
				},
				error : function(xhr, status, error)
				{
					alert("reple getdata error");
				}});
		}
	}

	function insertData()
	{
		$.ajax({
			type : "post",
			url : "${ initParam.root }/share/reple",
			dataType : "html",
			data : {
				board_seq : ${ data.board_seq },
				content : $("reple_content").val()
			},
			success : function(data, status, xhr)
			{
				$("#reple_content").val("");
			},
			error : function(xhr, status, error)
			{
				alert("reple insert error");
			}});
	}

	$(document).ready(function()
		{
			$("#reple_input").click(function()
				{
					insertData();
				});
		});
</script>
<div class="share-detail">
	<div class="share-detail-header">
		<span>
			<marquee scrollamount="5" behavior="scroll" width="300">${ data.board_title } - ${ data.board_artist }</marquee></span>
		<div id="select-info">
			<div id="col-date"><span class="column-infotitle">등록일</span><span id="column-date">${ regi_day }</span></div>
			<div id="col-view"><span class="column-infotitle">조회수</span><span id="column-view">${ data.check_cnt }</span></div>
		</div>
	</div>
</div>
<div class="share-detail-section">
	<div class="share-detail-mv">
		<embed src="//www.youtube.com/embed/${ data.videoID }?autoplay=1&controls=0&showinfo=0&rel=0&wmode='transparent'&allowfullscreen='false'" type="" width="400" height="300" />
	</div>
	<div class="share-detail-info">
		<table class="share-table">
			<tr>
				<td class="title"><pre>날   짜</pre></td>
				<td>${ data.weather_custom }</td>
			</tr>
			<tr>
				<td class="title">작성자</td>
				<td>${ data.userID }</td>
			</tr>
			<tr>
				<td id="desc" colspan="2">${ data.board_desc }</td>
			</tr>
		</table>
	</div>
</div>
<div class="share-button">
	<div class="paging-column">
		<input type="button" class="styled-button-detail" id="detail" value="이 전" onclick="setLink(null, 'share', 'content', { 'board_seq' : ${ prev_content } })" />
		<input type="button" class="styled-button-detail" id="detail" value="다 음" onclick="setLink(null, 'share', 'content', { 'board_seq' : ${ next_content } })" />
	</div>
</div>
<div id="reple_form">
	<input type="text" name="" id="reple_txt" size="68" />
	<input type="button" value="확인" id="reple_input" />
</div>
<div id="reple_list" style="size : 800px;"></div>