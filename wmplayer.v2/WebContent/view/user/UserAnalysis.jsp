<%@page import="kr.co.wmplayer.sinmina.dao.board.ShareboardDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserAnalysis.css" rel="stylesheet" />
<%


String userid = (String)session.getAttribute("success");

ShareboardDAO dao = new ShareboardDAO();

List<BoardUserDTO> list2 = dao.selectMyboard(userid); 

/*create table Like_Music(
  musicID varchar2(30) constraint musicID_fk references MusicInfo(musicID),
  userID varchar2(20) constraint likemusic_userID_fk references UserInfo(userID),
  like_date date not null
);
*/
%>

<div class="useranalysis-form">
	<div class="useranal-header">
		<!-- <span id="name">'������'</span>&nbsp;
		<span>���� ��ȣ�ϴ� �帣�� </span>
		<span id="style">'Pop'</span>&nbsp;
		<span>�Դϴ�</span> -->
	</div>
	<div class="useranal-section">
		<div class="anal-left">
			<table>
				<tr>
					<td colspan="2" id="anal-th">
						<span>����� </span>
						<span id="anal-like">���ƿ�</span>
						<span>�� ���</span>
						<span id="anal-cnt"><%= list2.size() %> </span>
						<span>��</span>
					</td>
				</tr>
				<%for(int i = 0 ; i < list2.size(); i++){
				BoardUserDTO board = list2.get(i);
				String board_title = board.getBoard_title();
				String board_artist = board.getBoard_artist();
				int board_seq = board.getBoard_seq();
				%>
				<tr>
					<td class="like-rank"><%= i+1 %></td>
					<td class="like-rank-object"><a href="${initParam.root }/wmplayer/sharedetail.do?board_seq=<%= board_seq%>"><%=board_title %>-<%=board_artist %></a></td>
				</tr>
			<%} %>
			</table>
		</div>
		<div class="anal-right">
		<table>
			<tr>
				<td colspan="3" id="anal-th">
						<span>����� �ø� </span>
						<span id="anal-board">MusicVideo</span>
						<span id="anal-cnt"><%= list2.size() %> </span>
						<span>��</span>
				</td>
			</tr>
			<%for(int i = 0 ; i < list2.size(); i++){
				BoardUserDTO board = list2.get(i);
				String board_title = board.getBoard_title();
				String board_artist = board.getBoard_artist();
				int board_seq = board.getBoard_seq();
				%>
				<tr>
					<td class="like-rank"><%= i+1 %></td>
					<td class="like-rank-object"><a href="${initParam.root }/wmplayer/sharedetail/view.do?board_seq=<%= board_seq%>"><%=board_title %>-<%=board_artist %></a></td>
				</tr>
			<%} %>
		</table>
	</div>
	</div>
	<div class="anal-button">
		<input type="button" class="styled-button-login" id="edit" value="ȸ�� ����" onclick="location.href='${initParam.root}/wmplayer/update/edit.do?action=select&id=${success }'"/>
		&nbsp;<input type="button" class="styled-button-login" id="drop" value="ȸ�� Ż��" onclick="location.href='${initParam.root}/wmplayer/drop.do'"/>
	</div>
</div>
