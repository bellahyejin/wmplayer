<%@page import="java.util.ArrayList"%>
<%@page import="org.w3c.dom.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnDetail.css"
	rel="stylesheet" />
${varColumn_seq } 
${functionWarning } 
${functionWarning2 }
${varTotalPage }
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
var pageNum
$(document).ready(function(){
	$.post("../ajax/column/CallReple.jsp",
			{column_seq:column_seq,
			pageNo:1}, function(data){$('#AppendReple').append(data);			
			});	
	$(".pageNum").click(function(){
		pageNum = $(this).attr("value");
		
		$.post("../ajax/column/CallReple.jsp",
				{column_seq:column_seq,
			pageNo:pageNum}, function(data){
				
				$('#AppendReple').empty();			
				$('#AppendReple').append(data);	
				$("#repleTxt").val("");
			});	
	});
	      
	$("#repleInput").click(function(){
	var txt = $("#repleTxt").val();
	
	$.post("../ajax/column/InsertReple.jsp",
			{repleContent:txt,
			column_seq:column_seq}, function(data){	
				
				$.post("../ajax/column/CallReple.jsp",
						{column_seq:column_seq,
					pageNo:1}, function(data){
						
						$('#AppendReple').empty();			
						$('#AppendReple').append(data);	
						$("#repleTxt").val("");
					});	
			}
	);		
	});
	
});
</script>

<script>
function before(){
	
	location.href="columndetail?column_seq=${beforesu }";
}
function next(){
	
	location.href="columndetail?column_seq=${nextsu }";
}
function coldelete(column_seq) {
	location.href="columndelete?column_seq=${column_seq}";
}

</script>


<div style="overflow: auto">

	<div class="column-total">
		<div class="column-header">Column Detail</div>
		<div class="column-title">
			<span id="col-select-title">${column.title }</span>
			<div id="col-select-info">
				<div id="col-date">
					<span class="column-infotitle">일자</span> <span id="column-date">${column.update_day }</span>
				</div>
				<div id="col-view">
					<span class="column-infotitle">조회수</span> <span id="column-view">${column.view_cnt }</span>
				</div>
			</div>
		</div>
		<div class="column-contents">
			<pre>
${column.contents }
</pre>

		</div>



		<div class="column-button">
			<div class="paging-column">
				<input type="submit" class="styled-button-detail" id="detail"
					value="이전" onclick="before();warning2()" /> <input type="button"
					class="styled-button-detail" id="detail" value="다음"
					onclick="next();warning()" />

				<c:if test="${success == 'admin'}">
					<input type="button" name="delete" value="삭제"
						onclick="coldelete('${ column_seq }')">
				</c:if>
				<input type="button" class="styled-button-list" id="list" value="목록"
					onclick="location.href='${initParam.root}/wmplayer/columnlist/list.do'" />
			</div>
		</div>
		<br>

		<div>
			<!-- 리플시작 -->
			<input type="text" size="65" id="repleTxt"> <input
				type="button" value="확인" id="repleInput">
		</div>
		<div id="AppendReple" style="size: 800px;"></div>
		<div id="replePage">
			<center>
				<c:forEach begin="1" end="${repleTotal/10 }" var="page">
					<a class="pageNum" value="${page }">${page}</a>
				</c:forEach>
			</center>
		</div>

	</div>