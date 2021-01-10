
const login = document.getElementById('login');     //login button
const newAcc = document.getElementById('create');   // new Account button

let ws = new WebSocket("ws://127.0.0.1:8080");
    setTimeout(function () {
	if (ws.readyState === WebSocket.CLOSED) {
   		alert("Unable to establish connection to server - Try reloading page or waiting a few minutes and then reloading.");
	}
    }, 3000);

function myFunction() {
  let user = document.getElementById("myUsername").value;
  let pass = document.getElementById("myPassword").value;
  let loginPkt = {packet:"login", username:user, password:pass};
  ws.send(JSON.stringify(loginPkt));
}

ws.onmessage = function (message) {
	try {
		var json = JSON.parse(message.data);
		document.getElementById("playerCount").innerHTML = json.playersOnline;
		document.getElementById("gold").innerHTML = json.gold;
		document.getElementById("timePlayed").innerHTML = json.timePlayed;
		document.getElementById("loginResponse").innerHTML = json.loginResponse;
		console.log("ya peter");
	} catch (e) {
		//console.log('This doesn\'t look like a valid JSON: ', message.data);
		return;
	}
}


function createAccount(){
    let user = document.getElementById("myUsername").value;
    let pass = document.getElementById("myPassword").value;
    let loginPkt = {packet:"register", username:user, password:pass};
    ws.send(JSON.stringify(loginPkt));
}

function logoutPlayer(){
    let loginPkt = {packet:"logout"};
    ws.send(JSON.stringify(loginPkt));
}

//Event Handler
login.addEventListener('click',myFunction);
newAcc.addEventListener('click',createAccount);
logout.addEventListener('click',logoutPlayer);
