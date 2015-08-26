<%@page import="kr.co.wmplayer.sinmina.dao.music.MusicDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.music.LikeMusicDTO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("UTF-8");
	StringBuffer str = new StringBuffer();
	MusicDAO dao = new MusicDAO();

	String musicID = request.getParameter("musicid");
	String userID = (String) session.getAttribute("success");
	String status = request.getParameter("status");
	String location = "";	
	LikeMusicDTO like = new LikeMusicDTO(musicID, userID,location);
	if(musicID != null){
		if(status.equals("add")){
			dao.addLike(like);
			/* out.print(musicID); */
			System.out.println("like add : success");
		}else if(status.equals("delete")){
			dao.deleteLike(like);
			/* out.print(musicID); */
			System.out.println("like delete add: success");
		}else if(status.equals("select")){
			//전체를 담아줄 객체를 설정
			
			if(dao.selectLike(like)){
			//System.out.println( dao.selectLike(like));
		 	if(dao.selectLike(like)){
				out.write("like");
				System.out.println("like select success");
			}else{
				out.write("unlike");
				System.out.println("like select success");
			}
		}
	}else {
		System.out.println("musicId is null");
	}
	}
%>
