<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <%@ include file="LoggedHeader.jsp" %>
  <h2>${entry.title}</h2>
  <div>
  	${entry.text}
  </div>
  <p>Last modification: ${entry.lastModifiedAt}</p>
  
  <c:if test="${sessionScope['current.user.nick'].equals(nick)}">
  	<a href="${pageContext.request.contextPath}/servleti/author/${nick}/${eid}/edit">Edit post</a>
  </c:if>
  
  <h4>Comments:</h4>
  <ul>
  <c:forEach items="${comments}" var="comment">
  	<li><p>${comment.usersEMail} posted on ${comment.postedOn}:</p><p>${comment.message}</p></li>
  </c:forEach>
  </ul>
  <h4>Add a comment</h4>
  <form method="post">
  	<c:if test="${sessionScope['current.user.nick'] == null}">
  	E-mail: <input type="email" name="email" />
  	<br />
  	</c:if>
  	Message: <input type="text" name="message" />
  	<br />
  	<input type="submit" value="Sumbit" />
  </form>
  </body>
</html>