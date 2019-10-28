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
	<c:forEach items="${calculations}" var="calc">
		<tr><td>${calc.angle}</td><td>${calc.sin}</td><td>${calc.cos}</td></tr>
	</c:forEach>
	</table>
</body>
</html>