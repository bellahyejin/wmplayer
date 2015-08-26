<%@page import="kr.co.wmplayer.sinmina.dao.board.ColumnboardDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnDetail.css"
	rel="stylesheet" />


<%
	request.setCharacterEncoding("UTF-8");
	int column_seq = Integer.parseInt(request.getParameter("column_seq"));//게시글 번호
	out.print("<script>var column_seq="
	+column_seq+";</script>");//column_seq 스크립트 var 설정
	
	ColumnboardDAO dao = new ColumnboardDAO();
	ColumnBoardDTO column = dao.select(column_seq);
	dao.updatecnt(column_seq);
	request.setAttribute("column", column);

	//여기부터는 다음과 이전 버튼 설정
	List<String> seqList = dao.selectSeq();
	int size = seqList.size();
	System.out.println("배열 사이즈" + size);
	int nowIndex = seqList.indexOf("" + column_seq);
	int nextsu = 0;
	int beforesu = 0;

	if (nowIndex == 0) {
		nextsu = Integer.parseInt(seqList.get(0));
		beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
		out.print("<script>function warning(){alert('다음페이지가 없습니다');}</script>");
	} else if (nowIndex == size - 1) {
		nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
		beforesu = Integer.parseInt(seqList.get(size - 1));
		out.print("<script>function warning2(){alert('이전페이지가 없습니다');}</script>");

	} else {
		nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
		System.out.println("다음 인덱스" + nextsu);
		beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
		System.out.println("이전 인덱스" + beforesu);
	}

	//리플 페이징처리
	
int totalPage = dao.countReply(column_seq);

request.setAttribute("repleTotal", totalPage);
	
out.print("<script>var totalPage="+totalPage+"</script>");

%>
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
	
	location.href="${initParam.root }/wmplayer/columndetail.do?column_seq=<%=beforesu %>";
}
function next(){
	
	location.href="${initParam.root }/wmplayer/columndetail.do?column_seq=<%=nextsu %>";
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
					onclick="next();warning()" /> <input type="button"
					class="styled-button-list" id="list" value="목록"
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