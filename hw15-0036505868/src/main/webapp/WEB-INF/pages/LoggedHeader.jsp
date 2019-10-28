<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<h4>Login status</h4>
	<c:choose>
    <c:when test="${sessionScope['current.user.id'] == null}">
      <p>Not logged in</p>
    </c:when>
    <c:otherwise>
      <p>Logged in as: ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}</p>
      <a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
    </c:otherwise>
  </c:choose>
</div>
<hr />
<br />