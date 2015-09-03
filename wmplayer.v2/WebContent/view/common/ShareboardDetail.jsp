<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="${ initParam.root }/css/global.css" rel="stylesheet" />
<link type="text/css" href="${ initParam.root }/css/ShareboardDetail.css" rel="stylesheet" />
<div class="share-detail">
	<div class="share-detail-header">
		<span>
			<marquee scrollamount="5" behavior="scroll" width="300">${ data.board_title } - ${ data.board_artist }</marquee></span>
		<div id="select-info">
			<div id="col-date"><span class="column-infotitle">등록일</span><span id="column-date">${ data.regi_day.replace('-','/').substring(0,10) }</span></div>
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
				<td class="title"><pre>날   씨</pre></td>
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
		<c:if test="${ prev_content != 0 }"><input type="button" class="styled-button-detail" id="detail" value="이 전" onclick="setLink(null, 'share', 'content', { 'board_seq' : ${ prev_content } })" /></c:if>
		<c:if test="${ next_content != 0 }"><input type="button" class="styled-button-detail" id="detail" value="다 음" onclick="setLink(null, 'share', 'content', { 'board_seq' : ${ next_content } })" /></c:if>
		<input type="button" value="목록" class="styled-button-list" id="list" onclick="setLink(null, 'share', 'list')" />
	</div>
</div>
<div id="reple_form">
	<input type="text" id="reple_txt" size="68" />
	<input type="button" value="확인" id="reple_input" />
</div>
<div id="reple_list" width="550px"></div>
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script type="text/javascript">
	this.sequence = ${ data.board_seq };
	this.temp = 0;

	function getReple(temp)
	{
		this.data = {
				board_seq : this.sequence,
				blank : temp
			};

		if (!JSONDataCompare(this.prev_data, this.data))
		{
			this.prev_data = this.data;

			$.ajax({
				type : "post",
				url : "${ initParam.root }/share/reple/list.ajax",
				dataType : "html",
				data : this.data,
				success : function(data, status, xhr)
				{
					$("#reple_list").html(data);
				},
				error : function(xhr, status, error)
				{
					alert("reple getdata error");
				}});
		}
	}

	function insertReple()
	{
		$.ajax({
			type : "post",
			url : "${ initParam.root }/share/reple/insert.ajax",
			dataType : "html",
			data : {
				board_seq : this.sequence,
				contents : $("#reple_txt").val()
			},
			success : function(data, status, xhr)
			{
				alert_message(data, "추가");
				if (data != "rewrite") $("#reple_txt").val("");
			},
			error : function(xhr, status, error)
			{
				alert("reple insert error");
			}});
	}

	function updateReple(sequence, content)
	{
		$.ajax({
			type : "post",
			url : "${ initParam.root }/share/reple/update.ajax",
			dataType : "html",
			data : {
				sharereple_seq : sequence,
				board_seq : this.sequence,
				contents : content
			},
			success : function(data, status, xhr)
			{
				alert_message(data, "수정");
			},
			error : function(xhr, status, error)
			{
				alert("reple update error");
			}});
	}

	function deleteReple(sequence)
	{
		$.ajax({
			type : "post",
			url : "${ initParam.root }/share/reple/delete.ajax",
			dataType : "html",
			data : {
				sharereple_seq : sequence
			},
			success : function(data, status, xhr)
			{
				alert_message(data, "삭제");
			},
			error : function(xhr, status, error)
			{
				alert("reple delete error");
			}});
	}

	$(document).ready(function()
		{
			getReple(this.temp);

			$(document).ajaxSuccess(function()
				{
					event();
				});
		});

	function event()
	{
		$("#reple_input").unbind();
		$(".update").unbind();
		$(".delete").unbind();
		$("#updatebutton").unbind();

		$("#reple-content input").hide();

		$("#reple_input").click(function()
			{
				insertReple();
			});

		$(".update").click(function()
			{
				tr = $(this).parent().parent();
				tr.find("input").toggle(300);
			});

		$(".delete").click(function()
			{
				if (confirm("진짜로 삭제하시겠습니까?")) deleteReple($(this).parent().parent().attr("data-seq"));
			});

		$(".reple-update").click(function()
			{
				tr = $(this).parent().parent();
				updateReple(tr.attr("data-seq"), tr.find("#update-reple").val());
			});
	}

	function alert_message(data, action)
	{
		if (data == "success")
		{
			alert(action + "했습니다.");
			getReple(++(this.temp));
		}
		else if (data == "failed") alert("오류가 발생했습니다. 나중에 해주세요.");
		else if (data == "rewrite") alert("다시 입력해주세요");
	}
</script>
