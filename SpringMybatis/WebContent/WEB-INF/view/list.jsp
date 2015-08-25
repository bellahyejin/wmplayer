<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h3>Person List</h3>
	<hr>
	<table border="1">
		<tr>
			<th>Name</th>
			<th>Age</th>
			<th>Job</th>
		</tr>
		<c:forEach items="${list }" var="person">
			<tr>
				<td><a href="editForm?name=${person.name }">${person.name }</a></td>
				<td>${person.age }</td>
				<td>${person.job }</td>
			</tr>
		</c:forEach>
	</table>
	<a href="form">작성페이지로</a>
	<br>
	<b>검색</b>
	<hr>
	<form action="search" method="post">
		이름: <input type="text" name="name"> <input type="submit"
			value="검색"><br>
	</form>
	<form action="search" method="post">
		나이: <input type="text" name="age"> <input type="submit"
			value="검색"><br>
	</form>
</body>
</html>