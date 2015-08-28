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

	this.totalPage = ${ total_page };
	this.board_seq = ${ board_seq };

	function getData(page)
	{
		this.data = {
				board_seq : ${ detail.board_seq },
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
					alert("getdata error");
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
				board_seq : ${ detail.board_seq },
				content : $("reple_content").val()
			},
			success : function(data, status, xhr)
			{
				$("#reple_content").val("");
			},
			error : function(xhr, status, error)
			{
				alert("insert error");
			}});
	}

	$(document).ready(function()
		{
			$("#repleinput").click(function()
				{
					insertData();
				});
		});
</script>
<div class="share-detail">
	<div class="share-detail-header">
		<span>
			<marquee scrollamount="5" behavior="scroll" width="300">${ detail.board_title } - ${ detail.board_artist }</marquee></span>
		<div id="select-info">
			<div id="col-date"><span class="column-infotitle">등록일</span><span id="column-date">${ regi_day }</span></div>
			<div id="col-view"><span class="column-infotitle">조회수</span><span id="column-view">${ detail.check_cnt }</span></div>
		</div>
	</div>
</div>
<div class="share-detail-section">
	<div class="share-detail-mv">
		<embed src="//www.youtube.com/embed/${  }?autoplay=1&controls=0&showinfo=0&rel=0&wmode='transparent'&allowfullscreen='false'" type="" width="400" height="300" />
	</div>
	<div class="share-detail-info">
		<table class="share-table">
			<tr>
				<td class="title"><pre>날   짜</pre></td>
				<td>${ detail.weather_custom }</td>
			</tr>
			<tr>
				<td class="title">작성자</td>
				<td>${ detail.userid }</td>
			</tr>
			<tr>
				<td id="desc" colspan="2">${ detail.board_desc }</td>
			</tr>
		</table>
	</div>
</div>