var insurantId = null;

function clearForm() {
	document.getElementById("ins_surname").value = "";
	document.getElementById("ins_name").value = "";
	document.getElementById("ins_patronymic").value = "";
	document.getElementById("birth_date").value = "";
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
	tr.onclick  = getId;
	tr.tabindex = rowNumber;
	tr.style.background = "";
	tr.insertCell(0).innerHTML = row.surname;
	tr.insertCell(1).innerHTML = row.name;
	tr.insertCell(2).innerHTML = row.patronymic;
	tr.insertCell(3).innerHTML = dateParser(row.birthDate);
}

function getId() {
	var oldId = null;
	if(insurantId !== null)
		oldId = insurantId;
	
	insurantId = this.id;
	
	changeBackground(oldId, insurantId);
}

function changeBackground(oldId, newId) {
	if(oldId !== null) 
		document.getElementById(oldId).style.background = "";
	
	document.getElementById(insurantId).style.background = "#77dd9b";
}