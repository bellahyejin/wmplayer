<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO"%>
<%@page import="org.w3c.dom.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
${alertMsg }
<script>
function before(){
	location.href="noticedetail?notice_seq=${beforesu}";
}
function next(){
	location.href="noticedetail?notice_seq=${nextsu}";
}
</script>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnDetail.css" rel="stylesheet" />
<div class="notice-total">
   <div class="notice-header">
      Notice Detail
   </div>
   <div class="notice-title">
      <span id="not-select-title">${noticedetail.title }</span>
      <div id="not-select-info">
         <div id="not-date">
            <span class="notice-infotitle">일자</span>
            <span id="notice-date">${noticedetail.update_day }</span>
         </div>
         <div id="not-view">
            <span class="notice-infotitle">조회수</span>
            <span id="notice-view">${noticedetail.view_cnt }</span>
         </div>
      </div>
   </div>
   <div class="notice-contents">
<pre>
${noticedetail.contents }
</pre>
   </div>

   <div class="notice-button">
      <div class="paging-notice">
         <input type="submit" class="styled-button-detail" id="detail" value="이전" onclick="before();warning2()" />
         <input type="button"  class="styled-button-detail" id="detail" value="다음"  onclick="next();warning()" />
    
      </div>
      <div class="list-notice">
         <input type="button" class="styled-button-list" id="list" value="목록" onclick="location.href='notice'"/>
      </div>
   </div>
</div>
