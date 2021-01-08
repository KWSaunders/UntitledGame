
const login = document.getElementById('login');     //login button
const newAcc = document.getElementById('create');   // new Account button

let ws = new WebSocket("ws://127.0.0.1:8080");
    setTimeout(function () {
	if (ws.readyState === WebSocket.CLOSED) {
   		alert("Unable to establish connection to server - Try reloading page or waiting a few minutes and then reloading.");
	}
    }, 3000);

//document.getElementById("playerCount").innerHTML = ;
function myFunction() {
  let user = document.getElementById("myUsername").value;
  let pass = document.getElementById("myPassword").value;
  let loginPkt = {packet:"login", username:user, password:pass};
  //ws = new WebSocket("ws://127.0.0.1:8080");
  ws.send(JSON.stringify(loginPkt));
  ws.onmessage = function (event) {
   console.log(event.data);
   document.getElementById("demo").innerHTML = event.data;
  }
}


function createAccount(){
    let user = document.getElementById("myUsername").value;
    let pass = document.getElementById("myPassword").value;
    let loginPkt = {packet:"register", username:user, password:pass};
    //ws = new WebSocket("ws://127.0.0.1:8080");
    ws.send(JSON.stringify(loginPkt));
    ws.onmessage = function (event) {
     console.log(event.data);
     document.getElementById("demo").innerHTML = event.data;
    }
}

//Event Handler
login.addEventListener('click',myFunction);
newAcc.addEventListener('click',createAccount);


