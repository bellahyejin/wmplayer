<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserAnalysis.css" rel="stylesheet" />
<div class="useranalysis-form">
	<div class="useranal-header">
	</div>
	<div class="useranal-section">
		<div class="anal-left">
			<table>
				<tr>
					<td colspan="2" id="anal-th">
						<span>당신의 </span>
						<span id="anal-like">좋아요</span>
						<span>곡 목록</span>
						<span id="anal-cnt">${listsize }</span>
						<span>건</span>
					</td>
				</tr>
				<c:forEach items="${share }" var="list" varStatus="status">
				<tr>
					<td class="like-rank">${status.count }</td>
					<td class="like-rank-object"><a href="sharedetail?board_seq=${list.board_seq}">${list.board_title }-${list.board_artist }</a></td>
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
					<td class="like-rank-object"><a href="sharedetail?board_seq=${list.board_seq}">${list.board_title }-${list.board_artist }</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</div>
	<div class="anal-button">
		<input type="button" class="styled-button-login" id="edit" value="회원 수정" onclick="location.href='${initParam.root}/wmplayer/update/edit.do?action=select&id=${success }'"/>
		&nbsp;<input type="button" class="styled-button-login" id="drop" value="회원 탈퇴" onclick="location.href='${initParam.root}/wmplayer/drop.do'"/>
	</div>
</div>
