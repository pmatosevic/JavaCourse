<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <h1>Registration form</h1>
  <c:if test="${error_msg != null}">
  	<p>Error: ${error_msg}</p>
  </c:if>
	<form method="post">
		First name: <input type="text" name="first_name" value="${param.first_name}" />
		<br />
		Last name: <input type="text" name="last_name" value="${param.last_name}" />
		<br />
		Email: <input type="email" name="email" value="${param.email}" />
		<br />
		Nick: <input type="text" name="nick" value="${param.nick}" />
		<br />
		Password: <input type="password" name="password" />
		<br />
		<input type="submit" name="submit" value="Register" />
	</form>
  </body>
</html>
