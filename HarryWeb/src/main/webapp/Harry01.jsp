<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>九九乘法表</h1>
	<hr />
	<table border="1" width="100%">
		
			<%
			for (int k = 0; k < 3; k++){
				
				out.print("<tr>");
				for (int j = 0; j <= 3; j++){
					out.print("<td>");
					for (int i = 1; i <= 9; i++){
						int r = 2 * i;
						out.print(String.format("2 x %d = %d<br />", i, r));
					}
					out.print("</td>");
				}
				out.print("</tr>");
				
			}
			%>
		
		
	</table>
</body>
</html>