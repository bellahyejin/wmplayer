<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<link rel="stylesheet" href="${initParam.root }/css/style.css">
<link type="text/css" href="${initParam.root }/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root }/css/ShareboardList.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root }/css/ShareCard.css"
	rel="stylesheet" />
<script src="${initParam.root }/js/jquery.min.js"></script>
<script type="text/javascript">
var pageNum = 0;
var flag = false;
var params = 'weather_custom=';
$(document).ready(function(){
	sendAjaxSort('weather_custom=all,&page=1');	
});

function pagetrans(num){
	
	params +='&page='+num;
	
	if(!flag){
		sendAjaxSort(params);
		flag = true;
	}
}

function checkboxData(value){
		params = 'weather_custom=';
	$('input:checkbox[name="sort"]:checked').each(function(){
		params += "'"+this.value+"',";
	});
	flag = false;
	pagetrans(1);
}

function sendAjaxSort(params){
	alert(params)
	$.ajax({
		url: '${initParam.root}/ajax/sortData.jsp',
		type:'POST',
		data: params,
		dataType: 'text',
		success: function(data){
			$('#share-content').html(data);
		}
	});
}

</script>
<div id="shareboardlist">
	<div class="title_share">
		<span id="title_text">Share Music Video</span>
		<div class="sort-checkbox">
			<ul>
				<li>
					<input type="checkbox" name="sort" id="all" value="all" onchange="checkboxData(this)" checked="checked"/> 
					<label for="all">All</label>
				</li>
				<li>
					<input type="checkbox" name="sort" id="sun" value="맑음" onchange="checkboxData(this)"/> 
					<label for="sun">맑음</label>
				</li>
				<li>
					<input type="checkbox" name="sort" id="rain" value="비" onchange="checkboxData(this)"/> 
					<label for="rain">비</label>
				</li>
				<li>
					<input type="checkbox" name="sort" id="snow" value="눈" onchange="checkboxData(this)"/> 
					<label for="snow">눈</label>
				</li>
				<li>
					<input type="checkbox" name="sort" id="cloud" value="흐림" onchange="checkboxData(this)"/> 
					<label for="cloud">흐림</label>
				</li>
				<li>
					<input type="checkbox" name="sort" id="lowcloudy" value="바람" onchange="checkboxData(this)"/> 
					<label for="lowcloudy">바람</label>
				</li>
			</ul>
		</div>
	</div>
	<div id="share-content">
	</div>
</div>
