<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form action="edit" method="POST">
		�̸� : <input type="text" name="name"  value="${p.name }"><br>
		���� : <input type="text" name="age" value="${p.age }"><br>
		���� : <input type="text" name="job" value="${p.job }"><br>
		<input type="submit" value="����">
		<input type="button" value="����" onclick="location.href='delete?name=${p.name}'">
	</form>
	<a href="list">�������</a>
</body>
</html>