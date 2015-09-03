<%@page import="kr.co.wmplayer.sinmina.dao.reply.ColumnReplyDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.reply.ColumnReplyDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="${initParam.root}/css/ColumnDetail.css"
	rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<script>
var pageNum = "${varPageNum }";
	$(document).ready(function() {

		$("#AppendReple :input").hide();
		//$("#upDel :input").hide();

		$(".call_click #delete").click(function() {
			var delNum = $(this).attr("value");
			
			$.ajax({
				url : 'columnDeleteReple',
				type : 'POST',
				data : {
					 delNum : delNum,
			         pageNo : pageNum,
			         column_seq : "${column_num}"
				},
				success : function(data){
					alert("댓글이 삭제 되었습니다");
					
					$.ajax({
						url : "CallReple",
						type : "POST",
						data : {
							column_seq : column_seq,
							pageNo : pageNum
						},
						success : function(data){
							$('#AppendReple').empty();
							$('#AppendReple').html(data);
						}
					});
				}
			});
		});

		$(".call_click #update").click(function() {

			$(this).parent().parent().find(":input").toggle(300);

		});

		$(".call_click #updateButton").click(function() {

			var updatedContents = $(this).prev().val()
			var upNum = $(this).prev().attr("id");
			
			$.ajax({
				url : 'UpdateReple',
				type : 'POST',
				data : {
					upNum : upNum,
		            updatedContents : updatedContents,
		            pageNo : pageNum,
		            column_seq : "${column_num}"
				},
				success : function(data){
					alert("댓글이 수정 되었습니다");
					
					$.ajax({
						url : 'CallReple',
						type : 'POST',
						data : {
							column_seq : column_seq,
							pageNo : pageNum
						},
						success : function(data){
							$('#AppendReple').empty();
							$('#AppendReple').html(data);
						}
					});
				}
			});
		});
	});
</script>
<table style="width: 540px" class="call_click">
	<tr class="replytitle">
		<th style="width: 50%">내용</th>
		<th style="width: 10%">작성자</th>
		<th style="width: 20%">작성일</th>
		<th style="width: 10%"></th>
	</tr>
	<c:forEach items="${reples}" var="reple" varStatus="status">
		<tr>
			<td>${reple.contents}<br> 
				<input id="${reple.columnreply_seq}" type='text' size="30">
				<input id="updateButton" type="button" value="수정">
			</td>
			<td>${reple.userID}</td>
			<td style="font-size: small;">
				<center>${reple.submit_date}</center>
			</td>
			<c:if test="${reple.userID == success}">
				<td id="upDel" style="font-size: small;">
				<a id="update" value="${reple.columnreply_seq}">수정</a><br> 
				<a id="delete" value="${reple.columnreply_seq}">삭제</a></td>
			</c:if>
		</tr>
	</c:forEach>
</table>