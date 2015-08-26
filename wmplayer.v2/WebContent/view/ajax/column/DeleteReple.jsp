<%@page import="kr.co.wmplayer.sinmina.dao.reply.ColumnReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
int delNum = Integer.parseInt(request.getParameter("delNum"));
ColumnReplyDAO dao = new ColumnReplyDAO();
dao.delete(delNum);
%>
