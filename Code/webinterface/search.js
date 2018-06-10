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
}
