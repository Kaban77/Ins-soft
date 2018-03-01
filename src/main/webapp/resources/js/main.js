function sendRequest(method, controller, data, callback) { //отправка запроса на сервер
	const url = `/insSoft/${controller}`;//формируем URL
	const request = new XMLHttpRequest();
	
	if(method === "GET") {
		request.open(method, url + data, true);
		request.setRequestHeader("content-type", "application/text");
	}
	else {
		request.open(method, url, true);
		
		const header = document.getElementById("header").value;
		const token = document.getElementById("token").value;
		
		request.setRequestHeader(header, token);
		request.setRequestHeader("content-type", "application/json");
	}
	
	request.contentType = "application/json";
    request.setRequestHeader("cache-control", "no-cache");
    
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

function dateParser(date) {
	var temp = new Date(date);
	return temp.toLocaleDateString();
}