<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans|PT+Serif+Caption|Source+Sans+Pro" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/policy.css" />">
	
	<!--My script-->
	<script src="<c:url value="/resources/js/policy.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/main.js" />" type="text/javascript"></script>
<title>Полис</title>
</head>
<body>
<!-- VIN должен состоять из заглавных латинских букв кроме Q,O,I и цифр! -->
	<div class = "client_info">
		<h3>Данные клиента</h3>
		<div class = "field">
			<label for="client_name">ФИО клиента</label>
			<input type="text" id = "client_name">
			<button id = "search_client" onclick="location.href='#find_client';">
				<img src = "<c:url value="/resources/pictures/find.png" />" id = "img_find" style = "width: 18px; height: 18px;"/>
			</button>
		</div>
		<div class = "field">
			<label for="license_series">Серия ВУ</label>
			<input type="text" id = "license_series" maxlength="4">
		</div>
		<div class = "field">
			<label for = "license_number">Номер ВУ</label>
			<input type="text" id = "license_number" maxlength="6">
		</div>
	</div>
	
	<div class = "transport">
		<h3>Транспортное средство</h3>
		<div class = "car">
			<div class = "car_field">
				<label for = "brand_name">Марка</label>
				<input type="search" id = "brand_name">
			</div>
			<div class = "car_field">
				<label for = "model_name">Модель</label>
				<input type="search" id = "model_name">
			</div>
			<div class = "car_field">
				<label for = "year_of_issue">Год выпуска</label>
				<input type="number" id = "year_of_issue">
			</div>
		</div>
		<div class = "car">
			<div class = "car_field">
				<label for = "vin">VIN</label>
				<input type="text" id = "vin">
			</div>
			<div class = "car_field">
				<label for = "car_number">Гос. номер</label>
				<input type="text" id = "car_number">
			</div>
			<div class = "car_field">
				<label for = "power">Мощность двиг-ля, л.с.</label>
				<input type="number" id = "power">
			</div>
		</div>
	</div>
	<div class = "coefficients">
		<h3>Коэффициенты</h3>
		<div class = "coefficients_name">
			<ul>
			 <li>ТБ</li>
			 <li>КБМ</li>
			 <li>КМ</li>
			 <li>КС</li>
			 <li>КВС</li>
			 <li>КП</li>
			 <li>КО</li>
			 <li>КН</li>
			 <li>КМ</li>
			</ul>
		</div>
		
		<div class = "coefficients_value">
			<ul>
			 <li>1200</li>
			 <li>0.5</li>
			 <li>1</li>
			 <li>1 </li>
			 <li>1</li>
			 <li>1</li>
			 <li>1</li>
			 <li>1</li>
			 <li>2</li>
			</ul>
		</div>
		Премия:
	</div>
	<div id = "find_client" class = "modal_search">
		<div>
			<a href="#close" title="Закрыть" class="close">X</a>
			<h3>Поиск клиента</h3>
			<div class = "field">
				<input type="text" placeholder="Фамилия" id = "ins_surname">
			</div>
			<div class = "field">
				<input type="text" placeholder="Имя" id = "ins_name">
			</div>
			<div class = "field">
				<input type="text" placeholder="Отчество" id = "ins_patronymic">
			</div>
			<div class = "field">
				<input type="text" placeholder="Дата рождения" id = "birth_date" maxlength="10">
			</div>
			<div class = "field">
				<button id = "start_search" onclick="findClient()">Искать</button>
			</div>
			<div class = "field">
				<button id = "reset" onclick="clearForm()">Очистить</button>
			</div>
			<div id = "results" class = "show_results">
				<h4>Записи отсутствуют</h4>
			</div>
			<div class = "buttons">
				<button>Новый</button>
				<button>Выбрать</button>
			</div>
		</div>
	
	</div>
</body>
</html>