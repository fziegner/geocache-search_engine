const baseURL = "http://localhost:8080/geocache-search-engine/webapi/"
const searchURL = baseURL + "search/"

function executeRESTCall(){
    let query = document.getElementById("suche").value;
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", searchURL + query, false)
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
    let response = JSON.parse(xhttp.responseText);
    console.log(response);
	return response;
}

function toggle_visibility(id) {
       var e = document.getElementById(id);
       if(e.style.display == 'block')
          e.style.display = 'none';
       else
          e.style.display = 'block';
}

function pprint_json_to_console(response) {
	for(var i = 0; i < response.length; i++) {
		console.log("array index: " + i);
		var obj = response[i]
		for(var key in obj) {
			var value = obj[key];
			console.log(key +  ": " + value);
		}
	}
}

function pprint_json_to_website(response) {
	document.getElementById("json").innerHTML = JSON.stringify(response, undefined, 2);
}

function print_results(response) {
	var resultset = "Results:\n\n";
	for(var i = 0; i < response.length; i++) {
		resultset = resultset + "ID: " + i + "\n";
		var obj = response[i];
		console.log("id: " + i);
		for(var key in obj) {
			var value = obj[key];
			console.log(key);
			if(key == "name" || key == "coordinates" || key == "status" || key == "condition" || key == "hiddenAt" || key == "waypoint" || key == "cacheType" || key == "caseType" || key == "link" || key == "description") {
				if(key == "name") {
					resultset = resultset + "Name: " + value + "\n";
				}else if (key == "coordinates") {
					resultset = resultset + "Koordinaten: " + value + "\n";
				}else if (key == "status") {
					resultset = resultset + "Status: " + value + "\n";
				}else if (key == "condition") {
					resultset = resultset + "Zustand: " + value + "\n";
				}else if (key == "hiddenAt") {
					resultset = resultset + "Versteckt am: " + value + "\n";
				}else if (key == "waypoint") {
					resultset = resultset + "Wegpunkt: " + value + "\n";
				}else if (key == "cacheType") {
					resultset = resultset + "Cacheart: " + value + "\n";
				}else if (key == "caseType") {
					resultset = resultset + "Behälter: " + value + "\n";
				}else if (key == "link") {
					resultset = resultset + "Online: " + value + "\n";
				}else if (key == "description") {
					resultset = resultset + "Beschreibung: " + value + "\n";
				}else if (key == "logs") {
					resultset = resultset + "Logs: " + "PLACEHOLDER" + "\n";
				}
			}
		}
		resultset = resultset + "\n";
	}
	document.getElementById("json").innerHTML = resultset;
}