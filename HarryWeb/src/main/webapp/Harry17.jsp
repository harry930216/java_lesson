<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = callback;

	function callback() {
		console.log(xhttp.readyState);
	}

	function test1() {
		xhttp.open("GET", "brad18.jsp", true);
		xhttp.send();
	}
</script>
</head>
<body>
	<input type="button" onclick="test1()" value="test1" />
<body>
</html>