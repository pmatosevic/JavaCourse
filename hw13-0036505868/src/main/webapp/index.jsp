<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Index page</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h1>Welcome to this website</h1>
	<h3>Links:</h3>
	<ul>
		<li><a href="${pageContext.request.contextPath}/colors.jsp">Background color chooser</a></li>
		<li><a href="${pageContext.request.contextPath}/trigonometric?a=0&b=90">Calculate values of sin function</a></li>
		<li><a href="${pageContext.request.contextPath}/stories/funny.jsp">Read a funny story</a></li>
		<li><a href="${pageContext.request.contextPath}/report.jsp">OS usage survey</a></li>
		<li><a href="${pageContext.request.contextPath}/powers?a=1&b=100&n=3">Download Excel table with calculated powers</a></li>
		<li><a href="${pageContext.request.contextPath}/appinfo.jsp">Application information</a></li>
		<li><a href="${pageContext.request.contextPath}/glasanje">Voting for bands</a></li>
	</ul>
	
	<form action="trigonometric" method="GET">
		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
</body>
</html>