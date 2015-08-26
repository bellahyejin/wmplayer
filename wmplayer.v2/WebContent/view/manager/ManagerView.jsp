<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link type="text/css" href="${initParam.root}/css/ManagerView.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${ initParam.root }/js/jquery-2.1.4.js"></script>
	<script type="text/javascript"
	src="${ initParam.root }/js/StringConstructor.js"></script>
<script type="text/javascript">
	this.flag = false;
	this.pres_page = ${ pres_page };

	function member()
	{
		if (flag)
		{
			$.ajax({
				type : "post",
				async : true,
				url : "${ initParam.root }/wmplayer/manager/member_list.do",
				dataType : "html", // xml, json, html, script, jsonp, text
				data : {
					status : $("#query-status-select option:selected").attr("value"),
					gender : $(".query-radio:checked").attr("value"),
					search : $("#query-search-select option:selected").attr("value"),
					query_value : $("#query-text").val(),
					page : this.pres_page
				},
				success : function(data, status, xhr)
					{
						var temp = data.split("|");
						$("#member_table").html(temp[0]);
						$(".pager-container-manager").html(temp[1]);
						flag = false;
					},
				error : function(xhr, status, error)
					{
						alert("error");
					}
				});
		}
	}

	function ajaxStart()
	{
		this.pres_page = 1;
		flag = true;
		member();
	}

	$(document).ready(function()
		{
			flag = true;
			member();
			$(document).ajaxSuccess(function()
				{
					$(".button").on("click", function()
						{
							var data = $(this).attr("name").split("-");
							var action = "manager_" + data[0], id = data[1], href, page = ${ pres_page };
							if (data[0].equals("drop")) href = "drop/retire";
							else href = "manager/block";

							if (confirm("이 회원을 " + $(this).attr("value") + (data[0].equals("drop") ? ""  : (data[0].equals("current") ? "화" : "하") + "시겠습니까?"))) location.href = "${ initParam.root }/wmplayer/" + href + ".do?id=" + id + "&action=" + action + "&page=" + page;
						});

					$("#query-status-select").on("change", function()
						{
							ajaxStart();
						});
					$(".query-radio").on("change", function()
						{
							ajaxStart();
						});
					$("#button").on("click", function()
						{
							ajaxStart();
						});
				});
		});
</script>
</head>
<body>
	<div class="manager-view">
		<div class="manager-menu">
			회원 목록 | <a href="${ initParam.root }/wmplayer/manager/dropreason.do">탈퇴
			이유</a> |
			 통계 현황[<a href="${ initParam.root }/wmplayer/manager/chartLogin.do"> 로그인</a> |
		<a href="${ initParam.root }/wmplayer/manager/chartJoin.do">회원가입 </a>]
		</div>
		<div class="member-table">
			<div class="member-subject">
				회원 목록 
				<div class="member-filter">
					<select id="query-status-select">
						<option value="all">전체 회원</option>
						<option value="block">차단 회원</option>
						<option value="current">활성 회원</option>
					</select> 
					<input type="radio" name="gender" class="query-radio" value="all" checked="checked" />전체 
					<input type="radio" name="gender" class="query-radio" value="man" />남자 
					<input type="radio"name="gender" class="query-radio" value="woman" />여자
				</div>
			</div>
			<table id="member_table"></table>

			<div class="search">
				<select id="query-search-select">
					<option value="userid">ID</option>
					<option value="name">이름</option>
				</select> <input id="query-text" type="text" size="10" placeholder="검색해주세요"/> 
				<input class="search-button" type="button" id="button" value="검색"  />
			</div>
		</div>

		<div class="pager-container-manager"></div>
	</div>
</body>
</html>
