<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <%@ include file="LoggedHeader.jsp" %>
    <c:if test="${sessionScope['current.user.id'] == null}">
    	<c:if test="${message != null}"><p>${message}</p></c:if>
        <div>
		  	<form method="post">
		  	Nick: <input type="text" value="${param.nick}" name="nick" />
		  	<br />
		  	Password: <input type="password" name="password" />
		  	<input type="submit" value="Login" name="login" />
		  	</form>
		  </div>
		  
		  <p>
		  	Not registered? Register <a href="register">here</a>!
		</p>
	</c:if>
  
  	<h3>List of authors:</h3>
	<ul>
	<c:forEach items="${blogUsers}" var="author">
		<li><a href="author/${author.nick}">${author.nick}</a>: ${author.firstName} ${author.lastName}</li>
	</c:forEach>
	</ul>
  

  </body>
</html>
