const baseURL = "http://localhost:8080/geocache-search-engine/webapi/";
const searchURL = baseURL + "search/";
const extendedSearch = searchURL + "extended/";
const suggestions = baseURL + "suggest/";
const logs = baseURL + "logs/";
const index = baseURL + "index/create"

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
  let waypoints = getCurrentWaypoints(response);
  postLogs(waypoints);
	return response;
}

function executeExtendedSearchRESTCall() {
  let search = document.getElementById("suche").value;
	let hiddenAfter = document.getElementById("hiddenAfter").value;
	let coordinates =document.getElementById("coordinates").value;
  	let caseType = document.getElementById("caseType").value;
  	let condition = document.getElementById("condition").value;
	let cacheType = document.getElementById("cacheType").value;
	let terrain = document.getElementById("terrain").value;
	let status = document.getElementById("status").value;
  let difficulty = document.getElementById("difficulty").value;

  let query = search + "?";

  if(!(hiddenAfter === "")) {
    query = query + "hiddenAfter=" + hiddenAfter;
  }
  if(!(coordinates === "")) {
		query = query + "&coordinates=" + coordinates;
	}
	if(!(caseType === "")) {
		query = query + "&caseType=" + caseType;
	}
	if(!(condition === "")) {
		query = query + "&condition=" + condition;
	}
	if(!(cacheType === "")) {
		query = query + "&cacheType=" + cacheType;
	}
	if(!(terrain === "")) {
		query = query + "&terrain=" + terrain;
	}
	if(!(status === "")) {
		query = query + "&status=" + status;
	}
  if(!(difficulty === "")) {
    query = query + "&difficulty=" + difficulty;
  }
	let xhttp = new XMLHttpRequest();
	console.log("GET", extendedSearch + query);
	xhttp.open("GET", extendedSearch + query, false);
	xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send();
  let response = JSON.parse(xhttp.responseText);
  //console.log(response);
  let waypoints = getCurrentWaypoints(response);
  postLogs(waypoints);
	return response;
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
		for(var key in obj) {
			var value = obj[key];
			if(key == "name" || key == "coordinates" || key == "status" || key == "condition" || key == "hiddenAt" || key == "waypoint" || key == "cacheType" || key == "caseType" || key == "link" || key == "description" || key == "difficulty" || key == "terrain" || key == "logs") {
				if(key == "name") {
					resultset = resultset + "Name: ".bold() + value + "\n";
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
					resultset = resultset + "Logs: ".bold() + value + "\n";
          var logs = printCacheLogs(response, i);
          value = "Logs: \n  "  ;
          for(var j in logs) {
            value = value + logs[j] + "\n";
          }
          //console.log(value);
					createResult(key, value, i);
				} else if (key == "difficulty") {
          resultset = resultset + "Difficulty: ".bold() + "\n";
          value = "Difficulty: " + value;
          createResult(key, value, i);
        } else if (key == "terrain") {
          resultset = resultset + "Terrain: ".bold() + "\n";
          value = "Terrain: " + value;
          createResult(key, value, i);
        }
			}
		}
		resultset = resultset + "\n";
	}
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
    x.style.display = "inline-block";
  } else {
    x.style.display = "none";
  }
}

function getSuggestions() {
  let query = document.getElementById("suche").value;
  let xhttp = new XMLHttpRequest();
  console.log("GET", suggestions + query);
  xhttp.open("GET", suggestions + query, false);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send();
  let response = JSON.parse(xhttp.responseText);
  console.log(response);
  return response;
}

function autocomplete() {
  var suggestionsRe = getSuggestions();
  var list = document.getElementById('suche');
  for(var i = 0; i < suggestionsRe.length; i++) {
    var option = document.createElement('option');
    option.value = item;
    list.appendChild(option);
  }
}

function removeElement(id) {
  var element = document.getElementById(id);
  return element.parentNode.removeChild(element);
}

function parseSuggestions() {
  var suggestionsRe = getSuggestions();
  console.log(suggestionsRe);
  var jString = JSON.stringify(suggestionsRe);
  jString = jString.replace("{\"suggestions\":[", " ");
  jString = jString.replace("]}", " ");
  var choices = jString.split(',');
  console.log(jString);
  console.log(choices);
  return choices;
}

function postLogs(waypoints) {
  let ips = getCurrentIP();
  let waypoint = waypoints.join();
  if (waypoint == ""){
    waypoint = "No results";
  }
  let query = document.getElementById('suche').value;
  let time = getCurrentTime();
  let toLog = ips + "/" + waypoint + "/" + query + "/" + time;
  let xhttp = new XMLHttpRequest();
  xhttp.open("GET", logs + toLog, false);
  xhttp.send();
  console.log("GET", logs + toLog);
}

function getCurrentTime() {
  return Date.now();
}

function getCurrentIP() {
  return myip;
}

function getCurrentWaypoints(response) {
  let res = response;
  var waypoints = [];
  for(var i in res) {
    var key = i;
    var val = res[i];
    for(var j in val) {
      var sub_key = j;
      var sub_val = val[j];
      if(sub_key == "waypoint") {
        waypoints.push(sub_val);
      }
    }
  }
  return waypoints;
}

function printCacheLogs(response, objectID) {
  var obj = response[objectID];
  var resultset = [];
  for(var key in obj) {
    if(key == "logs") {
      //console.log(obj[key])
      var re = obj[key];
      for(var i in re) {
        resultset.push("|||||\n" + i + ".Log: ");
        //console.log(re[i]);
        var ob = re[i];
        for(var j in ob) {
          //console.log(j + ": " + ob[j])
          resultset.push(j + ": " + ob[j]);
        }
        //console.log("\n");
        resultset.push("|\n");
      }
    }
  }
  //console.log(resultset);
  return resultset;
}

function createIndexCall() {
  let xhttp = new XMLHttpRequest();
  xhttp.open("GET", index, false);
  xhttp.send();
  let response = JSON.parse(xhttp.responseText);
  console.log(response);
	return response;
}

function handle(e) {
  if(e.keyCode === 13) {
    print_results(executeRESTCall());
  }
}
