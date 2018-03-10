var policyId = null;
var insurantId = null;
var insurantIdForClick = null;
var brandId = null;

window.onload = function() {
	policyId = window.location.search.replace( "?policyId=", "");
	
	if(policyId === "") {
		policyId = null;
		return;
	}
		
	let list = document.getElementById("brandList");
	findModels(list.childNodes[1].id);
};

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
	let vin = document.getElementById("vin").value;
	let error = document.getElementById("print_error");
	
	while (error.firstChild)
		error.removeChild(error.firstChild);
		
	if(vin === "") {
		error.innerHTML = "<span>Заполните, пожалуйста поле VIN</span>";
		return false;
	}
	
	if(vin.includes("Q") || vin.includes("O") || vin.includes("I") || vin.match(/[a-z]/g) !== null ||
	   vin.match(/[А-Я]/g) !== null || vin.match(/[а-я]/g) !== null) {
		
		error.innerHTML = "<span>VIN должен состоять из заглавных латинских букв кроме Q,O,I и цифр!</span>";
		document.getElementById("vin").value = "";
				
		return false;
	}
	return true;
}

function findClient() {
	
	const params = "?name=" + encodeURIComponent(document.getElementById("ins_name").value) 
    	          +"&patronymic=" + encodeURIComponent(document.getElementById("ins_patronymic").value)
    	          +"&surname=" + encodeURIComponent(document.getElementById("ins_surname").value)
    	          +"&birthDate=" + encodeURIComponent(document.getElementById("birth_date").value);
	
	sendRequest("GET", "get-clients", params, showClients); 
}

function showClients(result) {
	
	let div = document.getElementById("results");
	while (div.firstChild) {
		div.removeChild(div.firstChild);
	}
	
	document.getElementById("select").hidden = true;
	
	if(result.length === 0) {
		div.innerHTML = "<h4>Записи отсутствуют</h4>";
		return;
	}
		
	let table = document.createElement("table");
	div.appendChild(table);
	
	tr = table.insertRow(0);
	tr.className = "header";
	tr.insertCell(0).innerHTML = "Фамилия";
	tr.insertCell(1).innerHTML = "Имя";
	tr.insertCell(2).innerHTML = "Отчество";
	tr.insertCell(3).innerHTML = "Дата рождения";
	tr.insertCell(4).innerHTML = "Серия ВУ";
	tr.insertCell(5).innerHTML = "Номер ВУ";
	
	for(let i = 0; i < result.length; i++) {
		insertRow(table, result[i], i);
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
	tr.insertCell(3).innerHTML = parseDate(row.birthDate);
	tr.insertCell(4).innerHTML = row.document.serial;
	tr.insertCell(5).innerHTML = row.document.number;
}

function getInurantId() {
	let oldId = null;
	if(insurantIdForClick !== null)
		oldId = insurantIdForClick;
	
	insurantIdForClick = this.id;//убрать
	
	changeBackground(oldId);
	document.getElementById("select").hidden = false;
}

function changeBackground(oldId) {
	if(document.getElementById(oldId) !== null) 
		document.getElementById(oldId).style.background = "";
	
	document.getElementById(insurantIdForClick).style.background = "#77dd9b";
}

function selectInsurant() {
	let insurantName = new String();
	insurantId = insurantIdForClick;
	for(i = 0; i < 3; i++) {
		insurantName += document.getElementById(insurantId).childNodes[i].innerHTML + " ";
	}
	location.href='#close';
	
	document.getElementById("client_name").value = insurantName.trim();
	document.getElementById("license_series").value = document.getElementById(insurantId).childNodes[4].innerHTML;
	document.getElementById("license_number").value = document.getElementById(insurantId).childNodes[5].innerHTML;
}

function newClient() {
	clearForm();
	location.href='#new_client';
}

function saveInsurant() {	
	const doc = {
			serial: document.getElementById("serial").value,
			number: document.getElementById("number").value,
			docType: "3",
			dateOfIssue: document.getElementById("year").value
	};
	
	const data = {
			id: 0,
			surname: document.getElementById("surname").value.trim(),
			name: document.getElementById("name").value.trim(),
			patronymic: document.getElementById("patronymic").value.trim(),
			birthDate: document.getElementById("birth").value,
			sex: document.getElementById("sex").value,
			document: doc
	};
	
	if(checkNewInsurant(data)) {
		sendRequest("POST", "save-insurant", data, finishSaveInsurant);
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
	
	if(checkIsValidDate(data.birthDate) === false) {
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
	
	if(checkIsValidDate(data.document.dateOfIssue) === false) {
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
	if(id !== undefined) {
		insurantId = id;
		
		location.href='#close';
		document.getElementById("client_name").value = document.getElementById("surname").value + " " +
													   document.getElementById("name").value + " " +
													   document.getElementById("patronymic").value.trim();
		document.getElementById("license_series").value = document.getElementById("serial").value;
		document.getElementById("license_number").value = document.getElementById("number").value;
		
		alert("Данные сохранены!");
	}
	else
		alert("Ошибка при сохранении!");
}

function findBrands() {
	const params = "?brand=" + encodeURIComponent(document.getElementById("brand_name").value);
	
	sendRequest("GET", "get-brands", params, setBrandList);
}

function setBrandList(data) {
	let list = document.getElementById("brandList");
	
	while (list.firstChild)
		list.removeChild(list.firstChild);
	
	for(let i = 0; i < data.length; i++) {
		let option = document.createElement("option");
		option.id = data[i].BRAND_ID;
		option.innerHTML = data[i].BRAND_NAME;
		list.appendChild(option);
	}
}

function setBrandId() {
	let list = document.getElementById("brandList");
	let brandName = document.getElementById("brand_name").value;

	for(let i = 0; i < list.childNodes.length; i++) {
		let temp = list.childNodes[i].value;
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
	const params = "?brandId=" + brandId;
	
	sendRequest("GET", "get-models", params, setModelList);
}

function setModelList(data) {
	let list = document.getElementById("model_name");
	
	while (list.firstChild)
		list.removeChild(list.firstChild);
	
	for(let i = 0; i < data.length; i++) {
		let option = document.createElement("option");
		option.value = data[i].MODEL_ID;
		option.innerHTML = data[i].MODEL_NAME;
		list.appendChild(option);
		
		if(list.name === data[i].MODEL_NAME)
			list.selectedIndex = i;
	}
}

function checkFields() {	
	return !(((insurantId === null || brandId === null) && policyId === null) || 
			   document.getElementById("power").value === "" || document.getElementById("year_of_issue").value === "" || !checkVin());
	
}

function savePolicy() {
	if(checkFields()) {
		let button = document.getElementById("issue_policy");
		if(button !== null)
			button.parentNode.removeChild(button);
		
		let policy = {
				policyId: policyId,
				insurantId: insurantId,
				brandId: brandId,
				modelId: document.getElementById("model_name").value,
				yearOfIssue: document.getElementById("year_of_issue").value,
				vin: document.getElementById("vin").value,
				registerSign: document.getElementById("car_number").value,
				enginePower: document.getElementById("power").value
		};
		
		sendRequest("POST", "save-policy", policy, finishSavePolicy);
	}
	else
		alert("Заполите, пожалуйста, все поля!");
	
}

function finishSavePolicy(coefficents) {
	
	if(coefficents.premium !== 0) {
		
		document.getElementById("tariff").innerHTML = coefficents.tariff;
		document.getElementById("bonus").innerHTML = coefficents.bonus;
		document.getElementById("power_coeff").innerHTML = coefficents.power;
		document.getElementById("season").innerHTML = coefficents.season;
		document.getElementById("experience").innerHTML = coefficents.ageAndExperience;
		document.getElementById("period").innerHTML = coefficents.period;
		document.getElementById("driver_lim").innerHTML = coefficents.driverLimit;
		document.getElementById("territory").innerHTML = coefficents.territory;
		document.getElementById("premium").innerHTML = "Премия: " + coefficents.premium.toFixed(2);
		
		policyId = coefficents.policyId;
		
		let button = document.createElement("button");
		let div = document.getElementById("control_buttons");
		
		button.id = "issue_policy";
		button.onclick = issuePolicy;
		button.innerHTML = "Оформить";
		
		div.appendChild(button);
		
		alert("Полис сохранён успешно!");
	}
	else
		alert("Ошибка при сохранении полиса!");
}

function issuePolicy() {
	if(checkFields()) {
		sendRequest("POST", "issue-policy", policyId, hideItems);
	}
	else
		alert("Заполите, пожалуйста, все поля!");
}

function hideItems() {
	let button = document.getElementById("issue_policy");
	button.parentNode.removeChild(button);
	
	document.getElementById("search_client").disabled = "disabled";
	document.getElementById("brand_name").disabled = "disabled";
	document.getElementById("model_name").disabled = "disabled";
	document.getElementById("year_of_issue").disabled = "disabled";
	document.getElementById("vin").disabled = "disabled";
	document.getElementById("car_number").disabled = "disabled";
	document.getElementById("power").disabled = "disabled";
	document.getElementById("save_policy").disabled = "disabled";
	
	alert("Полис оформлен!");
}
