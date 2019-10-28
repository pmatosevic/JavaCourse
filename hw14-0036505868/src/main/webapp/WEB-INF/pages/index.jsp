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
	<h1>Active polls</h1>
	<ul>
	<c:forEach items="${polls}" var="poll">
		<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
	</c:forEach>
	</ul>
</body>
</html>