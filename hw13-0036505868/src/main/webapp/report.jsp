<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>OS usage</title>
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img src="${pageContext.request.contextPath}/reportImage" />
</body>
</html>
