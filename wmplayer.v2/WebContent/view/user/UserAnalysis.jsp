<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserAnalysis.css" rel="stylesheet" />
<link rel="stylesheet" href="${initParam.root }/css/style.css">
<script type="text/javascript" src="${ initParam.root }/js/common.js"></script>
<script type="text/javascript" src="${ initParam.root }/js/JSONDataCompare.js"></script>
<div class="useranalysis-form">
	<div class="avgbpm">
		<div class="title"> 
			<span>평균</span>
			<span class="sub-title">BPM</span>
		</div>
		<div class="average">
			<span>130.5</span>
		</div>
	</div>
	<div class="useranal-header">
		<table>
			<tr>
				<td>비밀번호</td>
				<td>*******</td> <!-- DB에 저장되어있는 비밀번호 길이 만큼 *로 표시한다 -->
				<td><a><i class="icon-pencil3"></i></a></td>
			</tr>
			<tr>
				<td>이 름</td>
				<td>이혜진</td>
				<td><a><i class="icon-pencil3"></i></a></td> 
			</tr>
			<tr>
				<td>생년월일</td>
				<td>1993/08/08</td> <!-- 버튼 클릭시 관련 select 창이 뜬다  -->
				<td><a><i class="icon-pencil3"></i></a></td>
							<!-- introform.jsp 에 join 폼에 있는 형식을 참고 -->
			</tr>
			<tr>
				<td>이메일</td>
				<td>bela010@naver.com</td> <!-- 수정버튼 클릭시 관련 emailid 부분은  input text, 뒷부분은 select   -->
				<td><a><i class="icon-pencil3"></i></a></td>
								<!-- introform.jsp 에 join 폼에 있는 형식을 참고 -->
			</tr>
		</table>
	</div>
	<div class="useranal-section">
		<div class="anal-left">
			<table>
				<tr>
					<td colspan="2" id="anal-th">
						<span>당신의 </span>
						<span id="anal-like">좋아요</span>
						<span>곡 목록</span>
						<span id="anal-cnt">${musicsize }</span>
						<span>건</span>
					</td>
				</tr>
				<c:forEach items="${music }" var="list" varStatus="status">
				<tr>
					<td class="like-rank">${status.count }</td>
					<td class="like-rank-object">${list.title }-${list.artist }</td>
				</tr>
			</c:forEach>
			</table>
		</div>
		<div class="anal-right">
		<table>
			<tr>
				<td colspan="3" id="anal-th">
						<span>당신이 올린 </span>
						<span id="anal-board">MusicVideo</span>
						<span id="anal-cnt">${listsize } </span>
						<span>건</span>
				</td>
			</tr>
			<c:forEach items="${share }" var="list" varStatus="status">
				<tr>
					<td class="like-rank">${status.count }</td>
					<td class="like-rank-object"><a href="#" onclick="setLink(null, 'share', 'content', { 'board_seq' : ${list.board_seq}})">${list.board_title }-${list.board_artist }</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</div>
	<div class="anal-button">
		<input type="button" class="styled-button-login" id="edit" value="회원 수정" onclick="location.href='${initParam.root}/wmplayer/update/edit.do?action=select&id=${success }'"/>
		&nbsp;<input type="button" class="styled-button-login" id="drop" value="회원 탈퇴" onclick="location.href='dropform'"/>
	</div>
</div>
