<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> --%>

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
	<div class="client_info">
		<h3>Данные клиента</h3>
		<div class="field">
			<label for="client_name">ФИО клиента</label>
			<input type="text" id="client_name" disabled="disabled" value="${ policy.insutantFullName}">
			<button id="search_client" onclick="location.href='#find_client';" ${disabled}>
				<img src="<c:url value="/resources/pictures/find.png" />" id="img_find"/>
			</button>
		</div>
		<div class="field">
			<label for="license_series">Серия ВУ</label>
			<input type="text" id="license_series" maxlength="4" disabled="disabled" value="${ policy.licenseSerial}">
		</div>
		<div class="field">
			<label for="license_number">Номер ВУ</label>
			<input type="text" id="license_number" maxlength="6" disabled="disabled" value="${ policy.licenseNumber}">
		</div>
	</div>
	
	<div class="transport">
		<h3>Транспортное средство</h3>
		<div class="area">
			<div class="line">
				<label for="brand_name">Марка</label>
				<input type="text" id="brand_name" list="brandList" ${disabled}
				       onkeypress="findBrands()" onblur="setBrandId()" value="${ policy.brandName}">
					<datalist id="brandList">
						<option id="${ policy.brandId}">${ policy.brandName}</option>
					</datalist>
			</div>
			<div class="line">
				<label for="model_name">Модель</label>
				<select id="model_name" ${disabled} name="${ policy.modelName}">
				</select>
			</div>
			<div class="line">
				<label for="year_of_issue">Год выпуска</label>
				<input type="text" id="year_of_issue" maxlength="4" value="${ policy.yearOfIssueCar}" ${disabled}
				       onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode < 31">
			</div>
		</div>
		<div class="area">
			<div class="line">
				<label for="vin">VIN</label>
				<input type="text" id="vin" maxlength="17" onblur="checkVin()" value="${ policy.vin}" ${disabled}>
			</div>
			<div class="line">
				<label for="car_number">Гос. номер</label>
				<input type="text" id="car_number" value="${ policy.registerSign}" ${disabled}>
			</div>
			<div class="line">
				<label for="power">Мощность двиг-ля, л.с.</label>
				<input type="text" id="power" value="${ policy.enginePower}" ${disabled}
				       onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode < 31">
			</div>
		</div>
		<div id="print_error">
		</div>
	</div>
	<div class="coefficients">
		<h3>Коэффициенты</h3>
		<div class="coefficients_list">
			<table>
				<tr>
					<td>ТБ</td>
					<td id="tariff">${ policy.coeff.tariff}</td>
				</tr>
				<tr>
					<td>КБМ</td>
					<td id="bonus">${ policy.coeff.bonus}</td>
				</tr>
				<tr>
					<td>КМ</td>
					<td id="power_coeff">${ policy.coeff.power}</td>
				</tr>
				<tr>
					<td>КС</td>
					<td id="season">${ policy.coeff.season}</td>
				</tr>
				<tr>
					<td>КВС</td>
					<td id="experience">${ policy.coeff.ageAndExperience}</td>
				</tr>
				<tr>
					<td>КП</td>
					<td id="period">${ policy.coeff.period}</td>
				</tr>
				<tr>
					<td>КО</td>
					<td id="driver_lim">${ policy.coeff.driverLimit}</td>
				</tr>
				<tr>
					<td>КТ</td>
					<td id="territory">${ policy.coeff.territory}</td>
				</tr>
			</table>
		</div>
		
		<div class="premium">
			<span id="premium">
			<c:if test="${not empty policy}">
				Премия: ${ policy.coeff.premium}
			</c:if>
			</span>
		</div>
	</div>
	
	<div id="control_buttons">
		<button id="save_policy" onclick="savePolicy()" ${disabled}>Сохранить</button>
	</div>
	
	<!-- Modal page for search clients-->
	
	<div id="find_client" class="modal_search">
		<div>
			<a href="#close" title="Закрыть" class="close">X</a>
			<h3>Поиск клиента</h3>
			<div class="field">
				<input type="text" placeholder="Фамилия" id="ins_surname">
			</div>
			<div class="field">
				<input type="text" placeholder="Имя" id="ins_name">
			</div>
			<div class="field">
				<input type="text" placeholder="Отчество" id="ins_patronymic">
			</div>
			<div class="field">
				<input type="text" placeholder="Дата рождения" id="birth_date" maxlength="10">
			</div>
			<div class="field">
				<button id="start_search" onclick="findClient()">Искать</button>
			</div>
			<div class="field">
				<button id="reset" onclick="clearForm()">Очистить</button>
			</div>
			<div id="results" class="show_results">
				<h4>Записи отсутствуют</h4>
			</div>
			<div class="buttons">
				<button id="select" onclick="selectInsurant()" hidden="true">Выбрать</button>
				<button id="new_insurant" onclick="newClient()">Новый</button>
			</div>
		</div>
	</div>
	
	<!-- Modal page for add info about clients-->
	
	<div id="new_client" class="modal_create">
		<div>
			<a href="#close" title="Закрыть" class="close">X</a>
			<h3>Справочник</h3>
			<div class="area">
				<h4>Личный данные</h4>
				<div class="line">
					<label for="surname">Фамилия</label>
					<input type="text" id="surname">
				</div>
				<div class="line">
					<label for="name">Имя</label>
					<input type="text" id="name">
				</div>
				<div class="line">
					<label for="patronymic">Отчество</label>
					<input type="text" id="patronymic">
				</div>
				<div class="line">
					<label for="birth">Дата рождения</label>
					<input type="text" id="birth" maxlength="10" placeholder="ДД.ММ.ГГГГ"
					       onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode < 31 || event.charCode == 46">
				</div>
				<div class="line">
					<label for="sex">Пол</label>
					<select id="sex">
						<option value="M">Мужской</option>
						<option value="F">Женский</option>
					</select>
				</div>
			</div>
			<div class="area">
				<h4>Документы</h4>
				<div class="line">
					<label for="serial">Серия ВУ</label>
					<input type="text" id="serial" maxlength="4">
				</div>
				<div class="line">
					<label for="number">Номер ВУ</label>
					<input type="text" id="number" maxlength="6">
				</div>
				<div class="line">
					<label for="year">Стаж вождения с</label>
					<input type="text" id="year" maxlength="10" placeholder="ДД.ММ.ГГГГ"
					       onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode < 31 || event.charCode == 46">
				</div>
			</div>
			<div class="action">
				<button id="save" type="submit" onclick="saveInsurant()">Сохранить</button>
				<button id="cancel" onclick="location.href='#find_client';">Отмена</button>
				<input id="token" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<input id="header" type="hidden" name="${_csrf.parameterName}" value="${_csrf.headerName}"> 
			</div>
		</div>
	</div>
</body>
</html>
