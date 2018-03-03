function clearForm() {
	document.getElementById("from_date").value = "";
	document.getElementById("to_date").value = "";
	document.getElementById("pol_num").value = "";
}

function checkKeyDown (elementId) {
	var oldString = document.getElementById(elementId).value;
	element = document.getElementById(elementId);
	element.onkeyup = function(){this.value = this.value.replace(/[^0-9\.]/g,'')}
	
	if(oldString.length === 2 || oldString.length ===5)
		document.getElementById(elementId).value = oldString + ".";
}

function findPolicy() {  //Поиск результата по заданым данным
	
	var div = document.getElementById("show");
	if(div !== null)
		div.parentNode.removeChild(div);//предварительно удаляем старые записи, если они есть
	
	var params = new String();
	
	params = "?dateFrom=" + encodeURIComponent(document.getElementById("from_date").value) 
	        +"&dateTo=" + encodeURIComponent(document.getElementById("to_date").value)
	        +"&policyNumber=" + encodeURIComponent(document.getElementById("pol_num").value);

	sendRequest("GET", "get-policies", params, showResult); 
}

function showResult(result) {
	
	if(result.length !== 0) {
		var div = document.createElement('div');
		div.className = "show_result";
		div.id = "show";
		div.innerHTML = "<h3>Результаты поиска:</h3>";
		var table = document.createElement('table');
		div.appendChild(table);
		
		tr = table.insertRow(0);
		tr.insertCell(0).innerHTML = "№ п/п";
		tr.insertCell(1).innerHTML = "№ полиса";
		tr.insertCell(2).innerHTML = "Срок действия";
		tr.insertCell(3).innerHTML = "ТС";
		tr.insertCell(4).innerHTML = "Страхователь";
		for(i = 0; i < result.length; i++) {
			tr = table.insertRow(i + 1);
			
			tr.insertCell(0).innerHTML = i + 1;
			tr.insertCell(1).innerHTML = result[i].policyNumber;
			tr.insertCell(2).innerHTML = dateParser(result[i].beginDate) + " - " + dateParser(result[i].endDate);
			tr.insertCell(3).innerHTML = result[i].car;
			tr.insertCell(4).innerHTML = result[i].insurantName;
		}
	document.body.appendChild(div);
	}
}
