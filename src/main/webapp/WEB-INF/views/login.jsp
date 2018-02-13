<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
	<title>Home</title>
	<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<form:form method="POST"  action="check-user" modelAttribute="user" class="main">
		<div class="login">
			<div class="field">
				<form:input path="username" placeholder="Имя пользователя" required="true"/>
			</div>
			
			<div class="field">
				<form:password path="password" placeholder="Пароль" required="true"/>
			</div>
			
			<div class="button">
				<input type="submit" value="Войти">
			</div>
		</div>
		
		
	</form:form>

</body>
</html>
