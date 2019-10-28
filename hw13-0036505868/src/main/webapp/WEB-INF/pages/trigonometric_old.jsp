<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Trigonometric functions</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h1>Calculated results:</h1>
	<table>
	<tr><td>x</td><td>sin(x)</td><td>cos(x)</td></tr>
	<c:forEach begin="0" end="${size - 1}" step="1" var="i">
		<tr><td>${inputs[i]}</td><td>${calculations_sin[i]}</td><td>${calculations_cos[i]}</td></tr>
	</c:forEach>
	</table>
</body>
</html>