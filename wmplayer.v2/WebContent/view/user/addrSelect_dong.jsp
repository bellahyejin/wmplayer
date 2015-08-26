<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
		String top_code = request.getParameter("code");
		URL url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl." + top_code + ".json.txt");
		InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
		char ch[] = new char[512];
		int i;
		while ((i = isr.read(ch)) != -1) {
			out.print(new String(ch, 0, i));
		}
%>