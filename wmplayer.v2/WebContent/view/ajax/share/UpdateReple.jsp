<%@page import="kr.co.wmplayer.sinmina.dao.reply.ShareReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
int upNum = Integer.parseInt(request.getParameter("upNum"));
String updatedContents = request.getParameter("updatedContents");
ShareReplyDAO dao = new ShareReplyDAO();
dao.update(upNum, updatedContents);
%>
