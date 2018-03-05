var policyId = null;
var insurantId = null;
var brandId = null;

function clearForm() {
	document.getElementById("ins_surname").value = "";
	document.getElementById("ins_name").value = "";
	document.getElementById("ins_patronymic").value = "";
	document.getElementById("birth_date").value = "";
	document.getElementById("serial").value = "",
	document.getElementById("number").value = "";
	document.getElementById("year").value = "";
	document.getElementById("surname").value = "";
	document.getElementById("name").value = "";
	document.getElementById("patronymic").value = "";
	document.getElementById("birth").value = "";
}

function checkVin() {
	var vin = document.getElementById("vin").value;
	var error = document.getElementById("print_error");
	
	while (error.firstChild)
		error.removeChild(error.firstChild);
		
	if(vin === "") {
		error.innerHTML = "<span>Заполните, пожалуйста поле VIN</span>";
		return false;
	}
	
	else if(vin.includes("Q") || vin.includes("O") || vin.includes("I") || vin.match(/[a-z]/g) !== null ||
			vin.match(/[А-Я]/g) !== null || vin.match(/[а-я]/g) !== null) {
		
		error.innerHTML = "<span>VIN должен состоять из заглавных латинских букв кроме Q,O,I и цифр!</span>";
		document.getElementById("vin").value = "";
				
		return false;
	}
	else
		return true;
}

function findClient() {
	
	var params = new String();
	
	params = "?name=" + encodeURIComponent(document.getElementById("ins_name").value) 
    	    +"&patronymic=" + encodeURIComponent(document.getElementById("ins_patronymic").value)
    	    +"&surname=" + encodeURIComponent(document.getElementById("ins_surname").value)
    	    +"&birthDate=" + encodeURIComponent(document.getElementById("birth_date").value);
	
	sendRequest("GET", "get-clients", params, showResult); 
}

function showResult(result) {
	
	var div = document.getElementById("results");
	while (div.firstChild) {
		div.removeChild(div.firstChild);
	}
	
	insurantId = null;
	document.getElementById("select").hidden = true;
	
	if(result.length !== 0) {
		var table = document.createElement("table");
		div.appendChild(table);
		
		tr = table.insertRow(0);
		tr.className = "header";
		tr.insertCell(0).innerHTML = "Фамилия";
		tr.insertCell(1).innerHTML = "Имя";
		tr.insertCell(2).innerHTML = "Отчество";
		tr.insertCell(3).innerHTML = "Дата рождения";
		
		for(i = 0; i < result.length; i++) {
			insertRow(table, result[i], i);
		}
	}
	else {
		div.innerHTML = "<h4>Записи отсутствуют</h4>";
	}
	
}

function insertRow(table, row, rowNumber) {
	tr = table.insertRow(rowNumber + 1);
	tr.id = row.id;
	tr.className = "ins_row";
	tr.onclick  = getInurantId;
	tr.tabindex = rowNumber;
	tr.style.background = "";
	tr.insertCell(0).innerHTML = row.surname;
	tr.insertCell(1).innerHTML = row.name;
	tr.insertCell(2).innerHTML = row.patronymic;
	tr.insertCell(3).innerHTML = dateParser(row.birthDate);
}

function getInurantId() {
	var oldId = null;
	if(insurantId !== null)
		oldId = insurantId;
	
	insurantId = this.id;
	
	changeBackground(oldId, insurantId);
	document.getElementById("select").hidden = false;
}

function changeBackground(oldId, newId) {
	if(oldId !== null) 
		document.getElementById(oldId).style.background = "";
	
	document.getElementById(insurantId).style.background = "#77dd9b";
}

function selectInsurant() {
	var insurantName = new String();
	for(i = 0; i < 3; i++) {
		insurantName += document.getElementById(insurantId).childNodes[i].innerHTML + " ";
	}
	location.href='#close';
	document.getElementById("client_name").value = insurantName.trim();	
}

function newClient() {
	clearForm();
	location.href='#new_client';
}

function saveInsurant() {
	
	var sex;
	
	if(document.getElementById("sex").value === "Мужской")
		sex = "M";
	else
		sex = "F";
	
	var doc = {
			serial: document.getElementById("serial").value,
			number: document.getElementById("number").value,
			docType: "3",
			dateOfIssue: document.getElementById("year").value
	};
	
	var data = {
			id: 0,
			surname: document.getElementById("surname").value.trim(),
			name: document.getElementById("name").value.trim(),
			patronymic: document.getElementById("patronymic").value.trim(),
			birthDate: document.getElementById("birth").value,
			sex: sex,
			document: doc
	};
	
	if(checkNewInsurant(data) === true) {
		sendRequest("POST", "save-insurant", data, finishSaveInsurant);
	}
	else {
		return;
	}
}

function checkNewInsurant(data) {
	var i = 0;
	
	if(data.surname === "") {
		document.getElementById("surname").className = "error";
		i++;
	}
	else
		document.getElementById("surname").className = "";
	
	if(data.name === "") {
		document.getElementById("name").className = "error";
		i++;
	}
	else
		document.getElementById("name").className = "";
	
	if(tryParseDate(data.birthDate) === false) {
		document.getElementById("birth").className = "error";
		i++;
	}
	else
		document.getElementById("birth").className = "";
	
	if(data.document.serial.length !== 4 ) {
		document.getElementById("serial").className = "error";
		i++;
	}
	else
		document.getElementById("serial").className = "";
	
	if(data.document.number.length !== 6 ) {
		document.getElementById("number").className = "error";
		i++;
	}
	else
		document.getElementById("number").className = "";
	
	if(tryParseDate(data.document.dateOfIssue) === false) {
		document.getElementById("year").className = "error";
		i++;
	}
	else
		document.getElementById("year").className = "";
	
	if(i === 0)
		return true;
	else
		return false;
}

function finishSaveInsurant(id) {
	insurantId = id;
	
	location.href='#close';
	document.getElementById("client_name").value = document.getElementById("surname").value + " " +
												   document.getElementById("name").value + " " +
												   document.getElementById("patronymic").value.trim();
	
	document.getElementById("license_series").value = document.getElementById("serial").value;
	document.getElementById("license_number").value = document.getElementById("number").value;
	
	alert("Данные сохранены!");
}

function findBrands() {
	var params = "?brand=" + document.getElementById("brand_name").value;
	
	sendRequest("GET", "get-brands", params, setBrandList);
}

function setBrandList(data) {
	var list = document.getElementById("brandList");
	
	while (list.firstChild)
		list.removeChild(list.firstChild);
	
	for(i = 0; i < data.length; i++) {
		var option = document.createElement("option");
		option.id = data[i].BRAND_ID;
		option.innerHTML = data[i].BRAND_NAME;
		list.appendChild(option);
	}
}

function setBrandId() {
	var list = document.getElementById("brandList");
	var brandName = document.getElementById("brand_name").value;
	
	for(i = 0; i < list.childNodes.length; i++) {
		var temp = list.childNodes[i].value;
		if(brandName === temp) {
			brandId = list.childNodes[i].id;
			findModels(brandId);
			return;
		}	
	}
	document.getElementById("brand_name").value = "";
	brandId = null;
}

function findModels(brandId) {
	var params = "?brandId=" + brandId;
	
	sendRequest("GET", "get-models", params, setModelList);
}

function setModelList(data) {
	var list = document.getElementById("model_name");
	
	while (list.firstChild)
		list.removeChild(list.firstChild);
	
	for(i = 0; i < data.length; i++) {
		var option = document.createElement("option");
		option.id = data[i].MODEL_ID;
		option.innerHTML = data[i].MODEL_NAME;
		list.appendChild(option);
	}
}
