<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  	<%@ include file="LoggedHeader.jsp" %>
  	<h3>List of blogs for author: ${nick}</h3>
  	<ul>
    <c:forEach items="${entries}" var="blog">
      <li><a href="${nick}/${blog.id}">${blog.title}</a></li>
    </c:forEach>
    </ul>
    
    <c:if test="${sessionScope['current.user.nick'] == nick}">
    	<hr />
    	<a href="${sessionScope['current.user.nick']}/new">Create new blog entry</a>
    </c:if>
  </body>
</html>