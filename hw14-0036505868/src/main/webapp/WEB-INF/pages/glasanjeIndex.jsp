<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Glasanje </title>
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach items="${options}" var="option">
		<li><a href="glasanje-glasaj?id=${option.id}&pollID=${param.pollID}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>