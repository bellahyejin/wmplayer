<%@page import="kr.co.wmplayer.sinmina.dao.reply.ColumnReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@page import="org.w3c.dom.Document"%>
<%
System.out.print("insertReple까지 도착");
request.setCharacterEncoding("UTF-8");
int column_seq = Integer.parseInt(request.getParameter("column_seq")); //column_seq
String repleContent = request.getParameter("repleContent"); // 리플내용
String userId=(String)session.getAttribute("success");
ColumnReplyDAO dao = new ColumnReplyDAO();
dao.insert(column_seq, repleContent, userId);
%>