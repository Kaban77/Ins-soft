<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
	<title>Home</title>
	<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Arimo|Source+Sans+Pro" rel="stylesheet">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<c:url value="/login" var="loginUrl"/>

	<form:form method="POST" action="${loginUrl}" class="main" name="form_login">
		<div class="login">
			<div class="field">
				<input type="text" name="user" placeholder="Имя пользователя" required="true">
			</div>
			
			<div class="field">
				<input type="password" name="password" placeholder="Пароль" required="true">
			</div>
			
			<div class="button">
				<input type="submit" value="Войти">
			</div>
			<div class="remember">
				<input name="remember_me" type="checkbox">
				<label for="remember_me">Запомнить</label>
			</div>
			<c:if test="${not empty error}">
				<div class="error">
					${error}
				</div>
			</c:if>
		</div>
		
		
	</form:form>

</body>
</html>
