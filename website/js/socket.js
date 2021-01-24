var webSocket;
var json;
var serverIp = "ws:127.0.0.1:8080";

function initWebsocket() {
    webSocket = null;
    webSocket = new WebSocket(serverIp);
    initWebSocketFunctions()
}

function command(message) {
 	try {
 	json = JSON.parse(message);
	switch(json.packet) {
		case "loginResponse":
			console.log("LOGIN RESPONSE: ", json.loginResponse);
    			document["getElementById"]("login-message")["innerHTML"] = json.loginResponse;
        		if (json.loginResponse == "Loading...") {
				displayGame();
            			//enableLoginFunctionsGlobal = true
        		}
			break;
		case "updatePlayer":
			Object.keys(json).forEach(function(key) {
				//this is weird, needs major fixing
    			document["getElementById"](key)["innerHTML"] = json[key];
				console.log(key, " -> ", json[key]);
			});
			break;
		case "chat":
			//move this all into chat.js writeToChatBox function
			console.log("chat was hit");
		    	var chatSegment = "";
			var name = json.name;
			var levelChat = "123"
		    	var chatbox = document.getElementById("chat-area-div");    
			//chat icons
			var icon = json.icon;
			//if(icon == 1)
			//	chatSegment += "<span><img src='images/icons/donor-icon.gif' style='vertical-align: text-top;' width='20' height='20' alt='Donor'/> ";
			//else 
			if(icon == 2)
				chatSegment += "<span style='color:green;'><span class='chat-tag-contributor'>Contributor</span> ";
			else if(icon == 4)
				chatSegment += "<span style='color:#669999;'><span class='chat-tag-mod'>Moderator</span> ";
			else if(icon == 5)
				chatSegment += "<span style='color:#666600;'><span class='chat-tag-dev'>Dev</span> ";
			chatSegment += name;
			chatSegment += " (" + levelChat +"): </span>";       
			chatSegment += json.msg;	
			chatSegment += "<span>";
			chatSegment += "<br />";	
			var totalTextDiv = chatbox.innerHTML + chatSegment;
			chatbox.innerHTML = totalTextDiv;
			//chatbox.animate({ scrollTop:  55555555 }, 'slow');
			$("#chat-area-div").animate({ scrollTop:  55555555 }, 'slow');
			//if(isAutoScrolling)
			break;
		//case "displayGame":
			//console.log("ok going to display game block");
			//document["getElementById"]("login-screen")["style"]["display"] = "none";
        		//document["getElementById"]("game-screen")["style"]["display"] = "";	
   			//document["getElementById"]("top-status-bar")["style"]["display"] = "";
			//break;
	}

 	} catch (e) {
 		console.log('This doesn\'t look like a valid JSON: ', message);
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
	    showLoginScreen();
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