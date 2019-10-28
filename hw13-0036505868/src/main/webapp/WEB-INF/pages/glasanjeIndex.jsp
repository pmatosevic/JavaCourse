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
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
	<ol>
		<c:forEach items="${bands}" var="band">
		<li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>