<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="https://fonts.googleapis.com/css?family=Open+Sans|PT+Serif+Caption|Source+Sans+Pro" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/menu.css" />">

<!-- <script src="https://unpkg.com/react@15/dist/react.js"></script> -->
<!-- <script src="https://unpkg.com/react-dom@15/dist/react-dom.js"></script> -->
<!-- <script src="https://unpkg.com/babel-standalone@6.15.0/babel.min.js"></script> -->

<!--My script-->
<script src="<c:url value="/resources/js/menu.js" />" type="text/javascript"></script>

<title>Menu</title>
</head>
<body>
	<div class = "border">
		<h3 id = "title_journal">Журнал</h3>
		<div class = "main_input">
			<h3>Параметры поиска</h3>
			<div class = "field">
				<label for = "from_date">Дата с: </label>
				<input type = "text" maxLength = "10" id = "from_date" onkeypress="checkKeyDown('from_date')" placeholder="ДД.ММ.ГГГГ">
				<img src = "<c:url value="/resources/pictures/time.png" />" alt = "Выберите дату" title = "Выберите дату" class = "date"/>
			</div>
			<div class = "field">
				<label for = "to_date">Дата по: </label>
				<input type = "text" maxLength = "10" id = "to_date" onkeypress="checkKeyDown('to_date')" placeholder="ДД.ММ.ГГГГ">
				<img src = "<c:url value="/resources/pictures/time.png" />" alt = "Выберите дату" title = "Выберите дату" class = "date"/>
			</div>
			<div class = "field">
				<label for = "pol_num">Номер:</label>					
				<input type = "text" id = "pol_num" >
			</div>
		</div>
		<div class = "action">
			<h3>Действия</h3>
			<button id = "find_pol" onClick = "findPolicy()">
			Поиск договора
<!-- 				&nbsp; -->
			<img src = "<c:url value="/resources/pictures/find.png" />" id = "img_find"/>
			</button>
			<button id = "clear_all" onClick = "ClearForm()" type="reset">Сброс условий</button>
			<button id = "new_pol" onClick = "NewPolicy()">Новый</button>
		</div>
<!-- 		<div class = "show_result"> -->
<!-- 			<h3>Результаты поиска:</h3> -->
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 				<th>№ п/п</th> -->
<!-- 				<th>№ полиса</th> -->
<!-- 				<th>Срок действия</th> -->
<!-- 				<th>ТС</th> -->
<!-- 				<th>Страхователь</th> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 		</div> -->
	</div>
</body>
</html>