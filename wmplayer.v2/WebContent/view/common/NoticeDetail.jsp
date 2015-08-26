<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO"%>
<%@page import="org.w3c.dom.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	int notice_seq = Integer.parseInt(request.getParameter("notice_seq"));
	NoticeboardDAO dao = new NoticeboardDAO();
	NoticeBoardDTO notice = dao.select(notice_seq);
	dao.updateView(notice_seq);
	request.setAttribute("notice", notice);

	//여기부터는 다음과 이전 버튼 설정
	List<String> seqList = dao.selectSeq();
	int size = seqList.size();
	System.out.println("배열 사이즈" + size);
	int nowIndex = seqList.indexOf("" + notice_seq);
	int nextsu = 0;
	int beforesu = 0;

	if (nowIndex == 0) {
		nextsu = Integer.parseInt(seqList.get(0));
		beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
		out.print("<script>function warning(){alert('다음페이지가 없습니다');}</script>");
	} else if (nowIndex == size - 1) {
		nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
		beforesu = Integer.parseInt(seqList.get(size - 1));
		out.print("<script>function warning2	(){alert('이전페이지가 없습니다');}</script>");

	} else {
		nextsu = Integer.parseInt(seqList.get(nowIndex - 1));
		System.out.println("다음 인덱스" + nextsu);
		beforesu = Integer.parseInt(seqList.get(nowIndex + 1));
		System.out.println("이전 인덱스" + beforesu);
	}

	//여기부터는 다음과 이전 버튼 설정
%>
<script>


function before(){
	
	location.href="${initParam.root }/wmplayer/noticedetail.do?notice_seq=<%=beforesu %>";
}
function next(){
	
	location.href="${initParam.root }/wmplayer/noticedetail.do?notice_seq=<%=nextsu %>";
}


</script>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnDetail.css" rel="stylesheet" />
<div class="notice-total">
   <div class="notice-header">
      Notice Detail
   </div>
   <div class="notice-title">
      <span id="not-select-title">${notice.title }</span>
      <div id="not-select-info">
         <div id="not-date">
            <span class="notice-infotitle">일자</span>
            <span id="notice-date">${notice.update_day }</span>
         </div>
         <div id="not-view">
            <span class="notice-infotitle">조회수</span>
            <span id="notice-view">${notice.view_cnt }</span>
         </div>
      </div>
   </div>
   <div class="notice-contents">
<pre>
${notice.contents }
</pre>
   </div>

   <div class="notice-button">
      <div class="paging-notice">
         <input type="submit" class="styled-button-detail" id="detail" value="이전" onclick="before();warning2()" />
         <input type="button"  class="styled-button-detail" id="detail" value="다음"  onclick="next();warning()" />
    
      </div>
      <div class="list-notice">
         <input type="button" class="styled-button-list" id="list" value="목록" onclick="location.href='${initParam.root}/wmplayer/noticelist.do'"/>
      </div>
   </div>
</div>
