<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="${ initParam.root }/css/style.css" rel="stylesheet" />
<link rel="stylesheet" href="${ initParam.root }/css/global.css" type="text/css" />
<link rel="stylesheet" href="${ initParam.root }/css/ShareboardList.css" type="text/css" />
<link rel="stylesheet" href="${ initParam.root }/css/ShareCard.css" type="text/css" />
<div id="shareboardlist">
	<div class="title_share">
		<span id="title_text">Share Music Video</span>
		<div class="sort-checkbox">
			<ul>
				<li><input type="checkbox" name="sort" id="all" /><label for="all">All</label></li>
				<li><input type="checkbox" name="sort" id="sun" /><label for="sun">맑음</label></li>
				<li><input type="checkbox" name="sort" id="rain" /><label for="rain">비</label></li>
				<li><input type="checkbox" name="sort" id="snow" /><label for="snow">눈</label></li>
				<li><input type="checkbox" name="sort" id="cloud" /><label for="cloud">흐림</label></li>
				<li><input type="checkbox" name="sort" id="lowcloudy" /><label for="lowcloudy">구름 조금</label></li>
			</ul>
		</div>
	</div>
	<div id="share-content">
	<div id="list"></div>
		<div class="searchboard">
			<div class="search">
				<select class="search-select">
					<option id="board_title" value="board_title">제목</option>
					<option id="board_artist" value="board_artist">아티스트</option>
					<option id="userid" value="userid">작성자</option>
				</select>
				<input type="text" name="search" class="input-text" id="input-text" placeholder="검색어를 입력해주세요" />
				<input type="button" value="Search" class="input-button" />
				<div class="write">
					<input type="button" value="Write" class="search-button" id="share" onclick="setLink(null, 'share', 'write')" />
				</div>
			</div>
		</div>
	</div>
	<div class="pager-container-share"></div>
</div>
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/StringConstructor.js"></script>
<script type="text/javascript">
	this.pres_page = ${ pres_page };
	this.weather = "";

	function listData(weather, select, text)
	{
		this.data = {
			weather_custom : weather,
			search_select : select,
			search_text : text,
			page : this.pres_page
		};

		if (!JSONDataCompare(this.prev_data, this.data))
		{
			this.prev_data = this.data;
			$.ajax(
				{
				type : "post",
				url : "${ initParam.root }/share/listdata.ajax",
				dataType : "html",
				data : this.data,
				success : function(data, status, xhr)
				{
					var temp = data.split("|");
					$("#list").html(temp[0]);
					$(".pager-container-share").html(temp[1] == undefined || temp[1] == null ? "" : temp[1]);
				}});
		}
	}

	function startAjax(weather)
	{
		this.pres_page = 1;
		listData(weather, $(".search-select option:selected").attr("value"), $("#input-text").val());
	}

	$(document).ready(function()
		{
			weather = "${ param.weather_custom }" == "" ? "all" : "${ param.weather_custom }";
			search = "${ param.search_select }" == "" ? "board_title" : "${ param.search_select }";
			text = "${ param.search_text }" == "" ? "" : "${ param.search_text }";

			var weather_temp = weather.split(",");
			for (var i = 0; i < weather_temp.length; i++) $(".sort-checkbox :checkbox#"+ weather_temp[i]).attr("checked", "checked");
			$(".search-select option#" + search).attr("selected", "selected");
			$("#input-text").val(text);

			listData(weather, search, text);
			$(document).ajaxSuccess(function()
				{
					$(".sort-checkbox :checkbox[name='sort']").change(function()
						{
							startAjax(getWeather());
						});

					$("input:button[value='Search']").click(function()
						{
							startAjax(getWeather());
						});

					$("#input-text").keypress(function(e)
						{
							var key = e.keyCode || e.which;
							if (key == 13) startAjax(getWeather());
						});

					$("#input-text").focus(function()
						{
							if ($(this).val() == "") startAjax(getWeather());
						});

					$("#input-text").change(function()
						{
							if ($(this).val() == "") startAjax(getWeather());
						});

					$(".sort-checkbox :checkbox[id != 'all']").click(function()
						{
							var flag = true;
							$(".sort-checkbox :checkbox[id != 'all']").each(function()
								{
									flag &= !$(this).is(":checked");
								});
							$(".sort-checkbox :checkbox#all").prop("checked", !(flag == 0));
							startAjax(getWeather());
						});

					$(".sort-checkbox :checkbox#all").click(function()
						{
							$(".sort-checkbox :checkbox[id != 'all']").each(function()
								{
									$(this).prop("checked", !$(".sort-checkbox :checkbox#all").is(":checked"));
								});

							startAjax(getWeather());
						});

					$(".pager-container-share a").click(function()
						{
							$(this).prop("title")
						});
				});
		});

	function getWeather()
	{
		weather = "";
		$(".sort-checkbox :checkbox[name='sort']:checked").each(function(index)
			{
				weather += $(this).attr("id") + (index == $(".sort-checkbox :checkbox[name='sort']:checked").length - 1 ? "" : ",");
			});
		return weather;
	}
</script>
