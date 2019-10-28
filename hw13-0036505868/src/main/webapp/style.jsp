<%@ page language="java" contentType="text/css; charset=UTF-8"
    pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
table, th, td {
	border: 1px solid black;
}
body {
	background-color: #<c:out value="${pickedBgCol}" default="FFFFFF"/>
}
