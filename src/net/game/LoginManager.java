package net.game;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.simple.parser.ParseException;

import net.game.entity.player.Player;

public class LoginManager {
	
	public static void login(Session session, String input1, String input2) throws SQLException, ParseException, IOException {
		//lookup player by username
		//check to see if password matches
		
		
		//lazy method is going to just check if it's null
		/**
		 * Lookup
		 */
		var player = WebServer.database.getPassword(input1);
		if(player == null) {
			session.getBasicRemote().sendText("Account does not exist for " + input1);
			return;
		}
		
		if(WebServer.database.getPassword(input1).equals(input2)) {
			session.getBasicRemote().sendText("Logging in...");
			Player p = new Player();
			p.username = input1;
			p.password = input2;
			p.data = WebServer.database.load(p);
			p.session = session;
			GameEngine.players.add(p);
		} else {
			session.getBasicRemote().sendText("Invalid username or password");
		}
		
	}

}
