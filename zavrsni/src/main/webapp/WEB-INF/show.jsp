<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Prikaz slike</title>
	<style type="text/css">
		table, th, td {
			border: 1px solid black;
		}
	</style>
</head>
<body>
	<p>Ime datoteke: ${filename}</p>
	<p>Broj linija: ${cnt.lineCnt}</p>
	<p>Broj kružnica: ${cnt.circleCnt}</p>
	<p>Broj krugova: ${cnt.fillCircleCnt}</p>
	<p>Broj trokuta: ${cnt.triangleCnt}</p>
	<p>Slika: </p>
	
	<img src="image?img=${filename}" />
	
	<form action="main" method="get">
		<input type="submit" value="Povratak" />
	</form>
</body>
</html>