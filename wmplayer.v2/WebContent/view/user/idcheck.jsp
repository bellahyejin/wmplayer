<%@page import="kr.co.wmplayer.sinmina.dao.user.UserInfoDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% 
 // System.out.println(request.getParameter("writeid"));
  
  String checkid = request.getParameter("writeid");
  System.out.print(checkid);
  UserInfoDAO dao = new UserInfoDAO();
  
  int id = dao.selectcheck(checkid);
  System.out.println(id);
  if(id == 0){
	 out.print("enable");
  }else{
	 out.print("disable"); 
  }
%> 