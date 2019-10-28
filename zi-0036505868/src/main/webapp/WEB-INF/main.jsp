<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Popis datoteka</title>
	<style type="text/css">
		table, th, td {
			border: 1px solid black;
		}
	</style>
</head>
<body>
	<h1>Popis datoteka:</h1>
	<c:choose>
	<c:when test="${files.size() == 0}">
		<p>Nema datoteka</p>
	</c:when>
	<c:otherwise>
		<ul>
		<c:forEach items="${files}" var="file">
			<li><a href="show?img=${file}">${file}</a></li>
		</c:forEach>
		</ul>
	</c:otherwise>	
	</c:choose>
	
	<h1>Formular:</h1>
	<form method="post">
		Ime datoteke: <input type="text" name="filename" />
		<br />
		Sadržaj: <textarea name="filebody"></textarea>
		<br />
		<input type="submit" value="Spremi" />
	</form>
</body>
</html>