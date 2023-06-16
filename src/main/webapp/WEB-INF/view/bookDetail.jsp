<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${msg} a Book</title>
</head>
<body>
	<c:if test="${msg == 'Update'}">
		<form:form modelAttribute="book" action="../books/${book.id}" method="post">
			<!-- Rest of your code -->
		</form:form>
	</c:if>

	<c:if test="${msg == 'Add'}">
		<form:form modelAttribute="book" action="../books" method="post">
			<form:errors path="*" element="div" />
			<!-- Rest of your code -->
		</form:form>
	</c:if>

	<table>
		<tr>
			<td>Title:</td>
			<td><form:input type="text" path="title" value="${book.title}" /></td>
		</tr>
		<tr>
			<td>ISBN:</td>
			<td><form:input type="text" path="ISBN" value="${book.ISBN}" /></td>
		</tr>
		<tr>
			<td>Author:</td>
			<td><form:input type="text" path="author" value="${book.author}" /></td>
		</tr>
	</table>

	<input type="submit" value="${msg}" />

	<c:if test="${msg == 'Update'}">
		<form:form action="delete?bookId=${book.id}" method="post">
			<button type="submit">Delete</button>
		</form:form>
	</c:if>
</body>
</html>
