package net.game;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import net.game.entity.player.Player;

public class LoginManager {

	public static void login(Session session, String user, String pass)
			throws SQLException, ParseException, IOException {
		JSONObject json = new JSONObject();
		
		if (WebServer.getAccountsDatabase().lookup(user) == null) {
			json.put("loginResponse", "Invalid username or password");
			session.getBasicRemote().sendText(json.toJSONString());
			return;
		}

		if (!WebServer.getAccountsDatabase().getPassword(user).equals(pass)) {
			json.put("loginResponse", "Invalid username or password");
			session.getBasicRemote().sendText(json.toJSONString());
			return;
		}
		// If the account is already logged in then we need to kick this account off
		for (int i = 0; i < GameEngine.getPlayersOnline(); i++) {
			Player p = GameEngine.getPlayers().get(i);
			if (p.username.equals(user)) {
				p.logout();
			}
		}
		
		//fix this in this area or ignore it
		
		json.put("loginResponse", "Loading...");
		session.getBasicRemote().sendText(json.toJSONString());
		Player player = new Player();
		player.username = user;
		player.password = pass;
		player.data = WebServer.getAccountsDatabase().loadPlayerData(player);
		player.setSession(session);
		GameEngine.getPlayers().add(player);
		session.getBasicRemote().sendText("DISPLAY_GAME");

	}

}
