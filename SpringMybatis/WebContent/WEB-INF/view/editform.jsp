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
		이름 : <input type="text" name="name"  value="${p.name }"><br>
		나이 : <input type="text" name="age" value="${p.age }"><br>
		직업 : <input type="text" name="job" value="${p.job }"><br>
		<input type="submit" value="수정">
		<input type="button" value="삭제" onclick="location.href='delete?name=${p.name}'">
	</form>
	<a href="list">목록으로</a>
</body>
</html>