package net.game;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.game.entity.player.Player;

import static sbcc.Core.*;

import java.io.*;
import java.sql.SQLException;

@ClientEndpoint
@ServerEndpoint(value = "")
public class Connection {

	public static String getAddress(Session sess) {
		String parts = sess.getUserProperties().get("javax.websocket.endpoint.remoteAddress").toString();
		String addr = parts.split(":")[0].replace("/", "");
		return addr;
	}

	@OnOpen
	public void onWebSocketConnect(Session sess) throws IOException {
		sess.setMaxIdleTimeout(Long.MAX_VALUE);
		int online = GameEngine.getPlayersOnline();
		JSONObject json = new JSONObject();
		//json.put("packet", "players_online");
		json.put("playersOnline", online + "");
		json.put("loginResponse", "");
		sess.getBasicRemote().sendText(json.toJSONString());
		// sess.getBasicRemote().sendText(arg0);
		println("Client connected from: " + getAddress(sess));
	}

	@OnMessage
	public void onWebSocketText(String message, Session session) throws IOException, ParseException, SQLException {
		println(message);
		try {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(message);
		if (jsonObject.containsKey("packet")) {
			switch (jsonObject.get("packet").toString()) {
			case "login":
				String username = jsonObject.get("username").toString();
				String password = jsonObject.get("password").toString();
				//session.getBasicRemote().sendText("Connecting to server...");
				LoginManager.login(session, username, password);
				break;

			// account creation
			case "register":
				username = jsonObject.get("username").toString();
				password = jsonObject.get("password").toString();
				//session.getBasicRemote().sendText("Connecting to server...");
				AccountCreation.register(session, username, password);
				break;
				
			case "logout": 
				for(Player p : GameEngine.getPlayers()) {
					if(p.getSession().equals(session))
						p.logout();
				}
				break;

			case "fish_testtt":
				// GameEngine.playerHandler.players.get(index)
				// this kind of stuff definitely needs to be fixed!
//				for(Player p : GameEngine.players) {
//					if(p.getSession().equals(session))
//						Fishing.catchFish(p);
//				}
				break;
			}
		}
		} catch(RuntimeException e) {
			System.out.println("Unhandled message from client!");
		}
	}

	@OnClose
	public void onWebSocketClose(CloseReason reason, Session session) throws SQLException, IOException {
		println(getAddress(session) + " closed " + reason);
		// Handles players disconnecting!
		// find session for player using hashmap? to avoid looping through all players
		for (int i = 0; i < GameEngine.getPlayersOnline(); i++) {
			Player player = GameEngine.playerHandler.players.get(i);
			if (session.equals(player.getSession())) {
				player.logout();
			}
		}
	}

	@OnError
	public void onWebSocketError(Throwable cause) {
		System.out.println("onWebSocketError: ");
		cause.printStackTrace(System.err);
	}
}
