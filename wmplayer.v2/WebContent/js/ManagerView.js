function member()
{
	this.data = $("#data_print").serialize() + "&page=" + pres_page;

	if (!(this.prev_data == this.data))
	{
		this.prev_data = this.data;

		$.ajax({
		type : "post",
		async : true,
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
		$.getScript("${ initParam.root }/js/common.js");
		flag = true;
		member();
		$(document).ajaxSuccess(function()
			{
				this.count = 0;
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
				$("#query-text").on("change", function()
					{
						if ($(this).val() == "") ajaxStart();
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