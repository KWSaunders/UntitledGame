package net.game;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class AccountCreation {
	
	public static void register(Session session, String name, String pass) throws SQLException, ParseException, IOException {
		//simple method for creating accounts
		// will want to add restrictions for names, passwords and their lengths

		JSONObject json = new JSONObject();
		json.put("packet", "loginResponse");
		
		//Restricts characters for user name (no special symbols permitted)
		if (!name.matches("[A-Za-z0-9 ]+")) {
			json.put("loginResponse", "Invalid characters in username! Username must contain alphabetic or numeric characters only.");
			session.getBasicRemote().sendText(json.toJSONString());
			return;
		}
		
		//Username length must be between 1-20 chars
		if(name.length() > 20 || name.length() < 1) {
			json.put("loginResponse", "Username must be between 1-20 characters long");
			session.getBasicRemote().sendText(json.toJSONString());
			return;
		}
		
		if(pass.length() > 20 || pass.length() <= 4) {
			json.put("loginResponse", "Password must be at least 5 characters long!");
			session.getBasicRemote().sendText(json.toJSONString());
			return;
		}
		
		//Looks up if account already exists
		if(WebServer.getAccountsDatabase().lookup(name) != null) {
			json.put("loginResponse", "Username already exists!");
			session.getBasicRemote().sendText(json.toJSONString());
			return;
		}
		
		for(String s : WebServer.getCensoredWordsList()) {
			if(name.contains(s)) {
				json.put("loginResponse", "Username not valid!");
				session.getBasicRemote().sendText(json.toJSONString());
				return;
			}
		}

		WebServer.getAccountsDatabase().register(name.toLowerCase(), pass);
		json.put("loginResponse", "Account Created!");
		session.getBasicRemote().sendText(json.toJSONString());
		LoginManager.login(session, name, pass);
	}

}
