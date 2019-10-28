<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Background color chooser</title>
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h1>Colors:</h1>
	<ul>
		<li><a href="${pageContext.request.contextPath}/setcolor?color=FFFFFF">WHITE</a></li>
		<li><a href="${pageContext.request.contextPath}/setcolor?color=FF0000">RED</a></li>
		<li><a href="${pageContext.request.contextPath}/setcolor?color=00FF00">GREEN</a></li>
		<li><a href="${pageContext.request.contextPath}/setcolor?color=00FFFF">CYAN</a></li>
	</ul>
</body>
</html>