<%@page import="kr.co.wmplayer.sinmina.dao.reply.ColumnReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
int upNum = Integer.parseInt(request.getParameter("upNum"));
String updatedContents = request.getParameter("updatedContents");
ColumnReplyDAO dao = new ColumnReplyDAO();
dao.update(upNum, updatedContents);
%>
