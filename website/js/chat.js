var mutedPeople = [];
var isAutoScrolling = true;
var totalTextDiv = "";
var showTimestamps = true;
var lastPM = "";
var lastPMFrom = "";


function isMuted(username)
{
	
	for(var i = 0; i < mutedPeople.length; i++)
	{
		if(username.startsWith(mutedPeople[i]))
			return true;
	}
	return false;
}

function toggleTimestamp()
{
	if(showTimestamps)
	{
		showTimestamps = false;
		messageBox("Timestamps off.");
	}
	else
	{
		showTimestamps = true;
		messageBox("Timestamps on.");
	}
		
}

//credit goes to lux for this function
function timeFetch()
{     
          var d = new Date();
        if(d.getHours() < 10)
                {var dHours = "0" + d.getHours()}
        else
                {var dHours = d.getHours()}
       
        if(d.getMinutes() < 10)
                {var dMinutes = "0" + d.getMinutes()}
        else
                {var dMinutes = d.getMinutes()}
               
        var da = "[" + dHours + ":" + dMinutes + "] ";
		
		if(showTimestamps)
        return da;
		else
		return "";
}

function toggleAutoScroll()
{
	if(isAutoScrolling)
	{
		messageBox("Autoscroll Disabled");
		isAutoScrolling = false;
	}
	else
	{
		messageBox("Autoscroll Enabled");
		isAutoScrolling = true;
	}
		
}

function sendChat(msg)
{
	if(msg.length > 0)
	{
			var button = document.getElementById("send-chat-btn");
			var textbox = document.getElementById("textbox-chat");

			button.setAttribute('disabled', true);
			textbox.setAttribute('disabled', true);
			button.value = 'Sending...';

			setTimeout(function(){
				button.value = "Send";
				button.removeAttribute('disabled');
				textbox.removeAttribute('disabled');
				textbox.focus();
				if(msg.startsWith("/") && !msg.startsWith("/yell"))
					chatCommand(msg);
				else {
					let pkt = {packet:"chat", message:msg};
					console.log("Going to send", msg);
					webSocket.send(JSON.stringify(pkt));	
				}
				clearText();
			}, 200)
	}
}

function refreshChat(data)
{
	var chatbox = document.getElementById("chat-area-div");
	var output = data;
	
	var splitArray = data.split("~");
	var userChatting = splitArray[0];
	var levelChat = splitArray[1];
	var tag = splitArray[2];
	var icon = splitArray[3];
	var msg = splitArray[4];
	var isPM = splitArray[5];
	
	for(var i = 0; i < mutedPeople.length; i++)
	{
			if(mutedPeople[i] == userChatting)
				return;
	}
	
	var chatSegment = "";
	var timeStamp = timeFetch();
	if(isPM == 1)
	{
		
		chatSegment = "<span style='color:purple'>PM from " + "<span style='cursor:pointer;' oncontextmenu='searchPlayerHicores(\""+userChatting+"\");return false;' onclick='preparePM(\""+userChatting+"\")'>"+userChatting+"</span>" +": " + msg + "</span>";
		chatSegment += "<br />";
		lastPMFrom = userChatting;
		var totalTextDiv = chatbox.innerHTML + timeStamp  + chatSegment;
		chatbox.innerHTML = totalTextDiv;
		
		if(isAutoScrolling)
		$("#chat-area-div").animate({ scrollTop:  55555555 }, 'slow');
	
		return;
	}
	if(isPM == 2)
	{
		chatSegment = "<span style='color:purple'>sent PM to " + "<span style='cursor:pointer;' oncontextmenu='searchPlayerHicores(\""+userChatting+"\");return false;' onclick='preparePM(\""+userChatting+"\")'>"+userChatting+"</span>" +": " + msg + "</span>";
		chatSegment += "<br />";
		var totalTextDiv = chatbox.innerHTML + timeStamp  + chatSegment;
		lastPMFrom = userChatting;
		chatbox.innerHTML = totalTextDiv;
		
		if(isAutoScrolling)
		$("#chat-area-div").animate({ scrollTop:  55555555 }, 'slow');
	
		return;
	}
	if(isPM == 3) //yell message
	{
		chatSegment = "<span style='color:blue;'><span class='chat-tag-yell'>Server Message</span> " + msg + " </span>";
		chatSegment += "<br />";
		var totalTextDiv = chatbox.innerHTML + timeStamp  + chatSegment;
		lastPMFrom = userChatting;
		chatbox.innerHTML = totalTextDiv;
		
		if(isAutoScrolling)
		$("#chat-area-div").animate({ scrollTop:  55555555 }, 'slow');
	
		return;
	}
	if(icon == 1)
		chatSegment = "<img title='Maxed Skills' src='images/icons/stats.png' style='vertical-align: text-top;' width='20' height='20' alt='Maxed Skills'/>" + chatSegment;
    
	else if(icon == 2)
		chatSegment = "<img title='Master in Mining' src='images/icons/pickaxe.png' style='vertical-align: text-top;' width='20' height='20' alt='Master in Mining'/>" + chatSegment;
	else if(icon == 3)
		chatSegment = "<img title='Master in Crafting' src='images/icons/anvil.png' style='vertical-align: text-top;' width='20' height='20' alt='Master in Crafting'/>" + chatSegment;
	else if(icon == 4)
		chatSegment = "<img title='Master in Brewing' src='images/brewing/vialofwater_chat.png' style='vertical-align: text-top;' width='20' height='20' alt='Master in Brewinghiscores'/>" + chatSegment;
	else if(icon == 5)
		chatSegment = "<img title='Master in Farming' src='images/icons/watering-can.png' style='vertical-align: text-top;' width='20' height='20' alt='Master in Farming'/>" + chatSegment;
	else if(icon == 6)
        chatSegment = "<img title='Hardcore Account' src='images/icons/hardcoreIcon.png' style='vertical-align: text-top;' width='20' height='20' alt='Hardcore Account'/>" + chatSegment;
	else if(icon == 7)
        chatSegment = "<img title='Halloween 2015' src='images/icons/halloween2015.png' style='vertical-align: text-top;' width='20' height='20' alt='Halloween 2015'/>" + chatSegment;
	else if(icon == 8)
		chatSegment = "<img title='Halloween 2015' src='images/icons/archaeology.png' style='vertical-align: text-top;' width='20' height='20' alt='Halloween 2015'/>" + chatSegment;
	else if(icon == 9)
        chatSegment = "<img title='Chirstmas 2015' src='images/sigils/christmas2015.png' style='vertical-align: text-top;' width='20' height='20' alt='Halloween 2015'/>" + chatSegment;
	else if(icon == 10)
		chatSegment = "<img title='Master in Farming' src='images/magic/wizardHatIcon.png' style='vertical-align: text-top;' width='20' height='20' alt='Master in Farming'/>" + chatSegment;     
	else if(icon == 11)
		chatSegment = "<img title='Holiday' src='images/sigils/easter2016.png' style='vertical-align: text-top;' width='20' height='20' alt='Holiday Sigil'/>" + chatSegment;
	else if(icon == 12)
		chatSegment = "<img title='COOP' src='images/icons/groupTaskBadge5.png' style='vertical-align: text-top;' width='20' height='20' alt='COOP'/>" + chatSegment;
	else if(icon == 13)
		chatSegment = "<img title='cooking master' src='images/icons/cookingskill.png' style='vertical-align: text-top;' width='20' height='20' alt='Cooking Master'/>" + chatSegment;
	else if(icon == 14)
		chatSegment = "<img title='Halloween 2016' src='images/sigils/halloween2016.png' style='vertical-align: text-top;' width='20' height='20' alt='Halloween 2016'/>" + chatSegment;
	else if(icon == 15)
		chatSegment = "<img title='Chirstmas 2016' src='images/sigils/christmas2016.png' style='vertical-align: text-top;' width='20' height='20' alt='Christmas 2016'/>" + chatSegment;

	if(tag == 1)
		chatSegment += "<span><img src='images/icons/donor-icon.gif' style='vertical-align: text-top;' width='20' height='20' alt='Donor'/> ";
	else if(tag == 2)
		chatSegment += "<span style='color:green;'><span class='chat-tag-contributor'>Contributor</span> ";
	else if(tag == 4)
		chatSegment += "<span style='color:#669999;'><span class='chat-tag-mod'>Moderator</span> ";
	else if(tag == 5)
		chatSegment += "<span style='color:#666600;'><span class='chat-tag-dev'>Dev</span> ";
	
	
	chatSegment += "<span style='cursor:pointer;' oncontextmenu='searchPlayerHicores(\""+userChatting+"\");return false;' onclick='preparePM(\""+userChatting+"\")'>"+userChatting;
	chatSegment += " (" + levelChat +"): </span>";
	
	//make links clickable
	if(isValidURL(msg) && disableUrls == 0)
	{
		var msgArray = msg.split(" ");
		var newString = "";
		for(var i = 0; i < msgArray.length; i++)
		{
			console.log(msgArray[i]);
			if(isValidURL(msgArray[i]))
			{
				var linkFound = "";
				if(!msgArray[i].startsWith("http"))
					linkFound = "<a href='http://"+msgArray[i]+"' target='_blank'>"+msgArray[i]+"</a>" + " ";
				else			
					linkFound = "<a href='"+msgArray[i]+"' target='_blank'>"+msgArray[i]+"</a>" + " ";
				newString += linkFound;
			}
			else
				newString += msgArray[i] + " ";
		}
		
		chatSegment += newString;
	}
	else
		chatSegment += msg;
	
	chatSegment += "<span>";
	chatSegment += "<br />";
	
	totalTextDiv = chatbox.innerHTML + timeStamp  + chatSegment;
	chatbox.innerHTML = totalTextDiv;
	
	if(isAutoScrolling)
	$("#chat-area-div").animate({ scrollTop:  55555555 }, 'slow');
}

function modMutesPlayer(username, reason, timeInHours)
{
	if(username.length > 0 && reason.length > 0 && timeInHours.length > 0)
		send("MOD_MUTE=" + username + "~" + timeInHours + "~" + reason);
	else
		alert("WRONG INPUT");
}

function chatCommand(msg0)
{
	//alert(msg0);
	
	var command = msg0.substring(1);
	

	if(command.indexOf("[") > -1)
	{
		openDialogue("Error","Do not include the square brackets in your commands" ,"")
		return;
	}
	if(command.startsWith("mute"))
	{
		var usrMuted = command.split(" ")[1];
		mutedPeople.push(usrMuted);
	}
	else if(command.startsWith("smute"))
	{
		var usrMuted = command.split(" ")[1];
		document.getElementById("mute-username").value = usrMuted;
		$("#mute-player").dialog({ width: 300 });
	}
	else if(command.startsWith("command"))
	{
		openChatCommandsDialogue();
	}
	else if(command.startsWith("clear"))
	{
		document.getElementById("chat-area-div").innerHTML = "";
	}
	else if(command.startsWith("ipmute"))
	{
		send("IP_MUTE=" + command.substring(7));
	}
	else if(command.startsWith("report"))
	{
		send("REPORT_PLAYER=" + command.substring(7));
	}
	else if(command.startsWith("pm"))
	{
		//load lastPM
		//alert(command);
		lastPM = command.substring(3).split(" ")[0];
		send("PM=" + command.substring(3));
	}
	else if(command.startsWith("lookup"))
	{
		var user = command.substring(7).split(" ")[0];
		searchPlayerHicores(user);
	}
	
	else if(command == "removetag")
	{
		openDialogue("REMOVING DONOR TAG","This command removes your donor tag (20$/50$) and is NOT REVERSABLE.  Are you sure?", "REMOVE_TAG");
	}

}

function chatBoxZoom(zoom)
{
	var currentHeight = $('#chat-area-div').height();
	if(zoom == "Bigger")
	{
		if(currentHeight < 1000)
		{
			document.getElementById("chat-area-div").style.height = (currentHeight + 100) + "px";
		}
	}
	else if(zoom == "Smaller")
	{
		if(currentHeight > 50)
		{
			document.getElementById("chat-area-div").style.height = (currentHeight - 100) + "px";
		}
	}
}

function filterUsernameFromDataChat(data)
{
	return data.substr(0, data.indexOf('(')); 
}
//credit goes to smitty and lux


function isValidURL(urlChat)
{
	//I also url incode server side so dont even bother
	if(urlChat.indexOf('http') >= 0 || urlChat.indexOf('www.') >= 0 || urlChat.indexOf('.com') >= 0 || urlChat.indexOf('.ca') >= 0 || urlChat.indexOf('.co') >= 0 || urlChat.indexOf('.net') >= 0 || urlChat.indexOf('.us') >= 0){
		if(urlChat.indexOf('%22') >= 0 || urlChat.indexOf('%27') >= 0 || urlChat.indexOf('%3E') >= 0 || urlChat.indexOf('%3C') >= 0 || urlChat.indexOf('&#62;') >= 0 || urlChat.indexOf('&#60;') >= 0 || urlChat.indexOf(';') >= 0 || urlChat.indexOf('~') >= 0 || urlChat.indexOf('\"') >= 0 || urlChat.indexOf('<') >= 0 || urlChat.indexOf('>') >= 0 || urlChat.indexOf('javascript:') >= 0 || urlChat.indexOf('window') >= 0 || urlChat.indexOf('document') >= 0 || urlChat.indexOf('cookie') >= 0)
			return false;
		
		return true;
	}
	else return false;
}
function checkEnter(e, txt)
{
	if(e.keyCode == 13)
		sendChat(document.getElementById("textbox-chat").value);

	if(e.keyCode == 9 && lastPMFrom.length > 0)
	{
		document.getElementById("textbox-chat").value = "/pm " + lastPMFrom + " ";
		if(e.preventDefault)
			e.preventDefault();
		
		return false;
		
	}
}

//note that the user has a space after it automatically
function preparePM(user)
{
	user = user.replace(" ", "_");
	document.getElementById("textbox-chat").value = "/pm " + user + " ";
	document.getElementById("textbox-chat").focus();
}
function clearText()
{
	document.getElementById("textbox-chat").value = "";
}