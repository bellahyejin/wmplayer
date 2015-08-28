<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="${ initParam.root }/css/style.css" rel="stylesheet" />
<link rel="stylesheet" href="${ initParam.root }/css/global.css" type="text/css" />
<link rel="stylesheet" href="${ initParam.root }/css/ShareboardList.css" type="text/css" />
<link rel="stylesheet" href="${ initParam.root }/css/ShareCard.css" type="text/css" />
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script type="text/javascript">
	this.pres_page = ${ pres_page };
	this.weather = "";

	function listData(weather)
	{
		this.data = {
			weather_custom : weather,
			page : this.pres_page
		};

		if (!JSONDataCompare(this.prev_data, this.data))
		{
			this.prev_data = this.data;
			$.ajax(
				{
				type : "post",
				url : "${ initParam.root }/share/listdata",
				dataType : "html",
				data : this.data,
				success : function(data, status, xhr)
				{
					$("#share-content").html(data);
				},
				error : function(xhr, status, error)
				{
					alert("error");
				}});
		}
	}

	function startAjax(weather)
	{
		this.pres_page = 1;
		listData(weather);
	}

	$(document).ready(function()
			{
				listData("all");
				$(document).ajaxSuccess(function()
					{
						$(".sort-checkbox :checkbox[name='sort']").change(function()
							{
								weather = "";
								$(".sort-checkbox :checkbox[name='sort']:checked").each(function(index)
									{
										weather += $(this).attr("id") + (index == $(".sort-checkbox :checkbox[name='sort']:checked").length - 1 ? "" : ",");
									});
								startAjax(weather);
							});
					});
			});
</script>
<div id="shareboardlist">
	<div class="title_share">
		<span id="title_text">Share Muisc Video</span>
		<div class="sort-checkbox">
			<ul>
				<li><input type="checkbox" name="sort" id="all" checked="checked" /><label for="all">All</label></li>
				<li><input type="checkbox" name="sort" id="sun" /><label for="sun">맑음</label></li>
				<li><input type="checkbox" name="sort" id="rain" /><label for="rain">비</label></li>
				<li><input type="checkbox" name="sort" id="snow" /><label for="snow">눈</label></li>
				<li><input type="checkbox" name="sort" id="cloud" /><label for="cloud">흐림</label></li>
				<li><input type="checkbox" name="sort" id="lowcloudy" /><label for="lowcloudy">구름 조금</label></li>
			</ul>
		</div>
	</div>
	<div id="share-content"></div>
</div>
