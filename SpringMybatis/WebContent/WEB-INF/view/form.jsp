<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form action="insert" method="POST">
		이름 : <input type="text" name="name"><br>
		나이 : <input type="text" name="age"><br>
		직업 : <input type="text" name="job"><br>
		<button>등록</button>
	</form>
	<a href="list">목록으로</a>
</body>
</html>