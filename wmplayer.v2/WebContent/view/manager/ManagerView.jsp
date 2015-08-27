<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link type="text/css" href="${ initParam.root }/css/global.css" rel="stylesheet" />
<link type="text/css" href="${ initParam.root }/css/ManagerView.css" rel="stylesheet" />
<script type="text/javascript" src="${ initParam.root }/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/StringConstructor.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<script type="text/javascript">
	this.pres_page = ${ pres_page };

	function member()
	{
		this.data = {
				status : $("#query-status-select option:selected").attr("value"),
				gender : $(".query-radio:checked").attr("value"),
				search : $("#query-search-select option:selected").attr("value"),
				value : $("#query-text").val(),
				page : this.pres_page
			};

		if (!JSONDataCompare(this.prev_data, this.data))
		{
			this.prev_data = this.data;

			$.ajax({
				type : "post",
				url : "${ initParam.root }/manager/userinfo.ajax",
				dataType : "html", // xml, json, html, script, jsonp, text
				data : this.data,
				success : function(data, status, xhr)
				{
					var temp = data.split("|");
					$("#member_table").html(temp[0]);
					$(".pager-container-manager").html(temp[1]);
				},
				error : function(xhr, status, error)
				{
					alert("error");
				}});
		}
	}

	function ajaxStart()
	{
		this.pres_page = 1;
		member();
	}

	$(document).ready(function()
		{
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
					$("#query-text").keypress(function(e)
						{
							var key = e.keyCode || e.which;
							if (key == 13) ajaxStart();
						});
					$("#button").click(function()
						{
							ajaxStart();
						});

					$("#member_table tr").hover(function()
						{
							$(this).find(".button").css("background-color", "#f6f6f6");
						}, function()
						{
							$(this).find(".button").css("background-color", "#ffffff");
						});
					$(".button").hover(function()
						{
							$(this).css("color", "#81d4fa");
							$(this).css("cursor", "pointer");
						}, function()
						{
							$(this).css("color", "#000000");
							$(this).css("cursor", "none");
						});
					$(".button").click(function()
						{
							$(this).css("color", "#62c8f9");
						});
				});
		});
</script>
</head>
<body>
	<div class="manager-view">
		<div class="manager-menu">
			ȸ�� ��� |
			<a href="#" class="link" onclick="setLink(null, 'manager', 'dropreason')">Ż�� ����</a> |
			 ��� ��Ȳ[ <a href="#" class="link" onclick="setLink(null, 'manager', 'chartlogin')">�α���</a> |
		<a href="#" class="link" onclick="setLink(null, 'manager', 'chartjoin')">ȸ������</a> ]
		</div>
		<div class="member-table">
			<div class="member-subject">
				ȸ�� ���
				<div class="member-filter">
					<select id="query-status-select" name="status">
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
				<select id="query-search-select" name="search">
					<option value="userid">ID</option>
					<option value="name">�̸�</option>
				</select> <input id="query-text" name="value" type="text" size="10" placeholder="�˻����ּ���"/>
				<input class="search-button" type="button" id="button" value="�˻�"  />
			</div>
		</div>

		<div class="pager-container-manager"></div>
	</div>
</body>
</html>
