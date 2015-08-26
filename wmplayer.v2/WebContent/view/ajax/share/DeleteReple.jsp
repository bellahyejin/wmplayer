<%@page import="kr.co.wmplayer.sinmina.dao.reply.ShareReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
int delNum = Integer.parseInt(request.getParameter("delNum"));
ShareReplyDAO dao = new ShareReplyDAO();
dao.delete(delNum);
%>
