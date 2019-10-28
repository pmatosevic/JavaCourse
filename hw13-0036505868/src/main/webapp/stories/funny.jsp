<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! 
	private String generateColor() {
		String[] available = {"7F7F7F", "FF7777", "77FF77", "7777FF"};
		int rand = new Random().nextInt(4);
		return available[rand];
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Funny story</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.jsp">
</head>
<body>
	<h3>A short funny story</h3>
	<p style="color: #<%= generateColor() %>">
	Infinitely many mathematicians walk into a bar. The first says, "I'll have a beer." The second says, "I'll have half a beer." The third says, "I'll have a quarter of a beer." The barman pulls out just two beers. The mathematicians are all like, "That's all you're giving us? How drunk do you expect us to get on that?" The bartender says, "Come on guys. Know your limits."
	<br />
	<br />
	<br />
	When a statistician passes the airport security check, they discover a bomb in his bag. He explains. "Statistics shows that the probability of a bomb being on an airplane is 1/1000. However, the chance that there are two bombs at one plane is 1/1000000. So, I am much safer..."
	</p>
</body>
</html>