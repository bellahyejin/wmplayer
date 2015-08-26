<%@page import="kr.co.wmplayer.sinmina.dao.board.NoticeboardDAO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
System.out.print("jsp파일까지 왔음");
String title = request.getParameter("title");
String contents = request.getParameter("contents");
System.out.print("타이틀"+title);
NoticeboardDAO dao = new NoticeboardDAO();
Map<String,Object> map = new HashMap<String,Object>();
map.put("title", title);
map.put("contents", contents);

dao.insert(map);
%>