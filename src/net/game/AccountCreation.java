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

		//Restricts characters for user name (no special symbols permitted)
		if (!name.matches("[A-Za-z0-9 ]+")) {
			session.getBasicRemote().sendText("Invalid characters in username! Username must contain alphabetic or numeric characters only.");
			return;
		}
		
		//Username length must be between 1-20 chars
		if(name.length() > 20 || name.length() < 1) {
			session.getBasicRemote().sendText("Username must be between 1-20 characters long");
			return;
		}
		
		if(pass.length() > 20 || pass.length() < 4) {
			session.getBasicRemote().sendText("Password must be between at least 5 characters long!");
			return;
		}
		
		//Looks up if account already exists
		if(WebServer.getAccountsDatabase().lookup(name) != null) {
			session.getBasicRemote().sendText("Username already exists!");
			return;
		}
		
		for(String s : WebServer.wordFilter) {
			if(name.contains(s)) {
				session.getBasicRemote().sendText("Username not valid!");
				return;
			}
		}

		session.getBasicRemote().sendText("Creating account....");
		WebServer.getAccountsDatabase().register(name, pass);
		session.getBasicRemote().sendText("Account Created!");
	}

}
