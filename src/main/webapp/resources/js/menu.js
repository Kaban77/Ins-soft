function clearForm() {
	document.getElementById("from_date").value = "";
	document.getElementById("to_date").value = "";
	document.getElementById("pol_num").value = "";
}

function findPolicy() {  //Поиск результата по заданым данным
	let div = document.getElementById("show");
	if(div !== null)
		div.parentNode.removeChild(div);//предварительно удаляем старые записи, если они есть
	
	const params = "?dateFrom=" + encodeURIComponent(document.getElementById("from_date").value) 
	              +"&dateTo=" + encodeURIComponent(document.getElementById("to_date").value)
	              +"&policyNumber=" + encodeURIComponent(document.getElementById("pol_num").value);

	sendRequest("GET", "get-policies", params, showResult); 
}

function showResult(result) {
	if(result.length !== 0) {
		let div = document.createElement('div');
		div.className = "show_result";
		div.id = "show";
		div.innerHTML = "<h3>Результаты поиска:</h3>";
		let table = document.createElement('table');
		div.appendChild(table);
		
		tr = table.insertRow(0);
		tr.insertCell(0).innerHTML = "№ п/п";
		tr.insertCell(1).innerHTML = "№ полиса";
		tr.insertCell(2).innerHTML = "Срок действия";
		tr.insertCell(3).innerHTML = "ТС";
		tr.insertCell(4).innerHTML = "Страхователь";
		for(let i = 0; i < result.length; i++) {
			let tr = table.insertRow(i + 1);
			let link = "<a class='link' onclick='window.open(this.href); return(false);' href=open?policyId=" + 
			           result[i].policyId + ">" + result[i].policyNumber + "</a>";
			
			tr.insertCell(0).innerHTML = i + 1;
			tr.insertCell(1).innerHTML = link;
			tr.insertCell(2).innerHTML = parseDate(result[i].beginDate) + " - " + parseDate(result[i].endDate);
			tr.insertCell(3).innerHTML = result[i].car;
			tr.insertCell(4).innerHTML = result[i].insurantName;
		}
		document.body.appendChild(div);
	}
}
