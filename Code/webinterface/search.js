const baseURL = "http://5.83.162.120:8080/geocache-search-engine/webapi/";
const searchURL = baseURL + "search/";
const extendedSearch = searchURL + "extended/";

function executeRESTCall(){
    let query = document.getElementById("suche").value;
	//console.log(query);
    let xhttp = new XMLHttpRequest();
    console.log("GET", searchURL + query);
    xhttp.open("GET", searchURL + query, false);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
    let response = JSON.parse(xhttp.responseText);
    console.log(response);
	return response;
}

function executeExtendedSearchRESTCall() {
	let hiddenAfter = document.getElementById("hiddenAfter").value;
	let coordinates =document.getElementById("coordinates").value;
	let caseType = document.getElementById("caseType").value;
	let condition = document.getElementById("condition").value;
	let cacheType = document.getElementById("cacheType").value;
	let terrain = document.getElementById("terrain").value;
	let status = document.getElementById("status").value;
	
	if(hiddenAfter === "") {
		hiddenAfter = "NULL"
	}
	if(coordinates === "") {
		coordinates = "NULL"
	}
	if(caseType === "") {
		caseType = "NULL"
	}
	if(condition === "") {
		condition = "NULL"
	}
	if(cacheType === "") {
		cacheType = "NULL"
	}
	if(terrain === "") {
		terrain = "NULL"
	}
	if(status === "") {
		status = "NULL"
	}
	
	//let query = "hiddenAfter=" + document.getElementById("hiddenAfter").value +  "&coordinates=" + document.getElementById("coordinates").value + "&caseType=" + document.getElementById("caseType").value +  "&condition=" + document.getElementById("condition").value +  "&cacheType=" + document.getElementById("cacheType").value +  "&terrain=" + document.getElementById("terrain").value +  "&status=" + document.getElementById("status").value;
	let query = "hiddenAfter=" + hiddenAfter +  "&coordinates=" + coordinates + "&caseType=" + caseType +  "&condition=" + condition +  "&cacheType=" + cacheType +  "&terrain=" + terrain +  "&status=" + status;
	//console.log(query);
	let xhttp = new XMLHttpRequest();
	console.log("GET", extendedSearch + query);
	xhttp.open("GET", extendedSearch + query, false);
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
		var obj = response[i];
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
	document.getElementById("content").innerHTML = "";
	createArea("json", "content");
	var resultset = "Results:\n\n";
	for(var i = 0; i < response.length; i++) {
		resultset = resultset + "ID: ".bold() + i + "\n";
		createArea(i, "json");
		var obj = response[i];
		console.log("id: " + i);
		for(var key in obj) {
			var value = obj[key];
			console.log(key);
			if(key == "name" || key == "coordinates" || key == "status" || key == "condition" || key == "hiddenAt" || key == "waypoint" || key == "cacheType" || key == "caseType" || key == "link" || key == "description") {
				if(key == "name") {
					resultset = resultset + "Name: ".bold() + value + "\n";
					/*value = "Name: " + value;*/
					createResult(key, value, i);
				}else if (key == "coordinates") {
					resultset = resultset + "Koordinaten: ".bold() + value + "\n";
					value = "Koordinaten: " + value;
					createResult(key, value, i);
				}else if (key == "status") {
					resultset = resultset + "Status: ".bold() + value + "\n";
					value = "Status: " + value;
					createResult(key, value, i);
				}else if (key == "condition") {
					resultset = resultset + "Zustand: ".bold() + value + "\n";
					value = "Zustand: " + value;
					createResult(key, value, i);
				}else if (key == "hiddenAt") {
					resultset = resultset + "Versteckt am: ".bold() + value + "\n";
					value = "Versteckt am: " + value;
					createResult(key, value, i);
				}else if (key == "waypoint") {
					resultset = resultset + "Wegpunkt: ".bold() + value + "\n";
					value = "Wegpunkt: " + value;
					createResult(key, value, i);
				}else if (key == "cacheType") {
					resultset = resultset + "Cacheart: ".bold() + value + "\n";
					value = "Cacheart: " + value;
					createResult(key, value, i);
				}else if (key == "caseType") {
					resultset = resultset + "Behälter: ".bold() + value + "\n";
					value = "Behälter: " + value;
					createResult(key, value, i);
				}else if (key == "link") {
					resultset = resultset + "Online: ".bold() + value.link(value) + "\n";
					value = "Online: " + value;
					createResult(key, value, i);
				}else if (key == "description") {
					resultset = resultset + "Beschreibung: ".bold() + value + "\n";
					value = "Beschreibung: " + value;
					createResult(key, value, i);
				}else if (key == "logs") {
					resultset = resultset + "Logs: ".bold() + "PLACEHOLDER" + "\n";
					value = "Logs: " + value;
					createResult(key, value, i);
				}
			}
		}
		resultset = resultset + "\n";

	}
	/*document.getElementById("json").innerHTML = resultset;*/
}

function createArea(newId, bigId) {
    var resultArea = document.createElement("DIV");
	resultArea.setAttribute("id", newId);
	document.getElementById(bigId).appendChild(resultArea);
}

function createResult(newClass, result, resultNr) {
    var resultArea = document.createElement("DIV");
	resultArea.className = newClass;
    var result = document.createTextNode(result);
    resultArea.appendChild(result);
	if (newClass == "name"){
	document.getElementById(resultNr).prepend(resultArea);
	} else {
	document.getElementById(resultNr).appendChild(resultArea);
	}
}

function showExtendedSearchOptions() {
	var x = document.getElementById("extendedSearch");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}
