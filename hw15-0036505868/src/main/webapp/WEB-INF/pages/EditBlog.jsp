<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  	<%@ include file="LoggedHeader.jsp" %>
  	<h3>Edit or create new blog post</h3>
  	<form method="post">
  		Title: <input type="text" name="title" value="${title}" />
  		Text:  
  		<textarea name="text">${text}</textarea> 
  		<input type="submit" value="Sumbit" name="submit" />
  	</form> 
  </body>
</html>