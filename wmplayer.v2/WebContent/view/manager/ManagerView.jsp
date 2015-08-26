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

							if (confirm("�� ȸ���� " + $(this).attr("value") + (data[0].equals("drop") ? ""  : (data[0].equals("current") ? "ȭ" : "��") + "�ðڽ��ϱ�?"))) location.href = "${ initParam.root }/wmplayer/" + href + ".do?id=" + id + "&action=" + action + "&page=" + page;
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
			ȸ�� ��� | <a href="${ initParam.root }/wmplayer/manager/dropreason.do">Ż��
			����</a> |
			 ��� ��Ȳ[<a href="${ initParam.root }/wmplayer/manager/chartLogin.do"> �α���</a> |
		<a href="${ initParam.root }/wmplayer/manager/chartJoin.do">ȸ������ </a>]
		</div>
		<div class="member-table">
			<div class="member-subject">
				ȸ�� ��� 
				<div class="member-filter">
					<select id="query-status-select">
						<option value="all">��ü ȸ��</option>
						<option value="block">���� ȸ��</option>
						<option value="current">Ȱ�� ȸ��</option>
					</select> 
					<input type="radio" name="gender" class="query-radio" value="all" checked="checked" />��ü 
					<input type="radio" name="gender" class="query-radio" value="man" />���� 
					<input type="radio"name="gender" class="query-radio" value="woman" />����
				</div>
			</div>
			<table id="member_table"></table>

			<div class="search">
				<select id="query-search-select">
					<option value="userid">ID</option>
					<option value="name">�̸�</option>
				</select> <input id="query-text" type="text" size="10" placeholder="�˻����ּ���"/> 
				<input class="search-button" type="button" id="button" value="�˻�"  />
			</div>
		</div>

		<div class="pager-container-manager"></div>
	</div>
</body>
</html>
