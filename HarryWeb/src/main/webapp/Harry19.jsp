<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
		<script type="text/javascript">
			function test1(){
				let url = "brad20.jsp";
				$('#lottery').load(url);
			}
</script>
	</head>
	<body>
		Lottery: <span id="Lottery"></span><br />
		<input type="button" onclick="test1()" value="Test1" /><br />
		<input type="button" onclick="test2()" value="Test2" /><br />
		<input type="button" onclick="test3()" value="Test3" /><br />
		<input type="button" onclick="test4()" value="Test4" /><br />

</body>
</html>