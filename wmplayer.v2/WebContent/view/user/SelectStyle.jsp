<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>



<script type="text/javascript" src="../../js/SelectStyle.js">

</script>

<style type="text/css" dir="../../css/global.css"></style>
<style> body { font-family: 나눔바른고딕;}</style>
</head>
<body class="font">

	<table id="ta" width="400" height="265" border="0" radius="3"
		rborder="#999999" rbgcolor="white">


		<tr>
			<td>

				<center>
					<h3>원하는 곡 스타일을 선택해 주세요
				</center> <br>
				<form action="" method="post" name="style">
					<center style="word-spacing: 40pt;">

						<input type="radio" name="st" value="total">전체 <input
							type="radio" name="st" value="abroad">해외 <input
							type="radio" name="st" value="national">국내
					</center>
					<br>
					<center>
						<input type="submit" class="styled-button-login" id="join" value="회원가입" />
					</center>
				</form>
				</h3>
			</td>
		</tr>
	</table>
	<script>
		roundTable("ta");
	</script>


</body>
</html>