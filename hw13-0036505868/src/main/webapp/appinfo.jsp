<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="org.jfree.chart.ui.ApplicationFrame"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
	private String getFormattedDifference() {
		long startMillis = (Long) getServletContext().getAttribute("start_time");
		long currMillis = System.currentTimeMillis();
		
		long diff = currMillis - startMillis;
		
		long days = TimeUnit.MILLISECONDS.toDays(diff);
		diff -= TimeUnit.DAYS.toMillis(days);
		
		long hh = TimeUnit.MILLISECONDS.toHours(diff);
		diff -= TimeUnit.HOURS.toMillis(hh);
		
		long mm = TimeUnit.MILLISECONDS.toMinutes(diff); 
		diff -= TimeUnit.MINUTES.toMillis(mm);
		
		long ss = TimeUnit.MILLISECONDS.toSeconds(diff);
		diff -= TimeUnit.SECONDS.toMillis(ss);
		
		return days + " days " + hh + " hours " + mm + " minutes " + ss + " seconds and " + diff + "milliseconds";
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Application information</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h3>The application has been running for: <%= getFormattedDifference() %>.</h3>
</body>
</html>