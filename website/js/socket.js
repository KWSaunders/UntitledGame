var webSocket;
var json;

function initWebsocket() {
    webSocket = null;
    webSocket = new WebSocket("ws:127.0.0.1:8080");
    initWebSocketFunctions()
    document["getElementById"]("top-status-bar")["style"]["display"] = "none";
}

function command(message) {
 	try {
	if(message == 'DISPLAY_GAME') {
	        document["getElementById"]("login-screen")["style"]["display"] = "none";
        	document["getElementById"]("game-screen")["style"]["display"] = "";	
   		document["getElementById"]("top-status-bar")["style"]["display"] = "";
	}
 	json = JSON.parse(message);
 	//console.log(json);

	//console.log(Object.keys(json));

	Object.keys(json).forEach(function(key) {
    		document["getElementById"](key)["innerHTML"] = json[key];
			console.log(key, " -> ", json[key]);
	});

	//fix this only when send the loginResponse
        document["getElementById"]("login-message")["style"]["display"] = "";
        document["getElementById"]("login-message")["innerHTML"] = json.loginResponse;

        if (json.loginResponse == "Loading...") {
            enableLoginFunctionsGlobal = true
        }
 	} catch (e) {
 		console.log('This doesn\'t look like a valid JSON: ', message.data);
 		return;
 	}
}

function sendBytes(message) {
    if (webSocket.readyState == 3) {
        webSocket = new WebSocket(serverIp)
    };
    webSocket["send"](message)
}

function initWebSocketFunctions() {
    try {
        webSocket["onerror"] = function(_0x1E5B4) {
            showLoginMessageAsFailed()
        };
        webSocket["onopen"] = function(_0x1E5B4) {
            _0x1EC80(_0x1E5B4)
        };
        webSocket["onclose"] = function(_0x1E5B4) {
            _0x1EB7B(_0x1E5B4)
        };
        webSocket["onmessage"] = function(_0x1E5B4) {
            _0x1EC29(_0x1E5B4)
        };

        function _0x1EC29(_0x1E5B4) {
            command(_0x1E5B4["data"])
        }

        function _0x1EC80(_0x1E5B4) {
            websocketReadyGlobal = true;
            //sendBytes("PADE_LOAD")
        }

        function _0x1EB7B(_0x1E5B4) {}

        function _0x1EBD2(_0x1E5B4) {}
    } catch (err) {
        alert(err["message"])
    }
}