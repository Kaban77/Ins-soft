function NewPolicy() {
	//var params = "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes"
	//window.open("policy_page.html", "New Policy"/*, params*/)
}

function ClearForm() {
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

function sendRequest(method, controller, data, callback) { //отправка запроса на сервер
	const url = `/insSoft/${controller}`;//формируем URL
	const request = new XMLHttpRequest();
	request.contentType = "application/json";
    	request.setRequestHeader("content-type", "application/text");
    	request.setRequestHeader("cache-control", "no-cache");
	
	if(method === "GET") {
		request.open(method, url + data, true);
	}
	else
		request.open(method, url, true);
	
	if (data !== null && method !== "GET")
        request.send(JSON.stringify(data));
    else
        request.send();
		
	request.onreadystatechange = () => {
        if (request.readyState !== 4)
            return;
			
		if (request.status >= 200 && request.status < 300) {
				if (callback !== undefined && callback !== null) {
					const result = JSON.parse(request.responseText);
					callback(result);
				}
		} else {
				console.log(`${request.status}: ${request.statusText}`);
		}
	};
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
			tr.insertCell(2).innerHTML = result[i].beginDate + " - " + result[i].endDate;
			tr.insertCell(3).innerHTML = result[i].car;
			tr.insertCell(4).innerHTML = result[i].insurantName;
		}
	document.body.appendChild(div);
	}
}
