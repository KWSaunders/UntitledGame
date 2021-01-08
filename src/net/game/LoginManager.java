package net.game;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.simple.parser.ParseException;

import net.game.entity.player.Player;

public class LoginManager {
	
	public static void login(Session session, String user, String pass) throws SQLException, ParseException, IOException {
		
		var userExists = WebServer.getAccountsDatabase().lookup(user);
		if(userExists == null) {
			session.getBasicRemote().sendText("Invalid username or password");
			return;
		}
		
		if(WebServer.getAccountsDatabase().getPassword(user).equals(pass)) {
			// If the account is already logged in then we need to kick this account off
			for(int i = 0; i < GameEngine.getPlayersOnline(); i++) {
				Player p = GameEngine.playerHandler.players.get(i);
				if(p.username.equals(user)) {
					GameEngine.playerHandler.players.remove(i);
					session.close();
				}
			}
			session.getBasicRemote().sendText("Logging in...");
			Player player = new Player();
			player.username = user;
			player.password = pass;
			player.data = WebServer.getAccountsDatabase().loadPlayerData(player);
			player.setSession(session);
			GameEngine.playerHandler.players.add(player);
		} else {
			session.getBasicRemote().sendText("Invalid username or password");
		}
		
	}

}
