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
import net.game.entity.player.skills.Fishing;

import static sbcc.Core.*;

import java.io.*;
import java.sql.SQLException;

@ClientEndpoint
@ServerEndpoint(value = "")
public class Connection {

	public static String getAddress(Session sess) {
		return sess.getUserProperties().get("javax.websocket.endpoint.remoteAddress").toString();
	}


	@OnOpen
	public void onWebSocketConnect(Session sess) throws IOException {
		sess.setMaxIdleTimeout(Long.MAX_VALUE);
		int online = GameEngine.getPlayerCount();
		//sess.getBasicRemote().sendText(arg0);
		println("Client connected from: " + getAddress(sess));
	}

	@OnMessage
	public void onWebSocketText(String message, Session session) throws IOException, ParseException, SQLException {
		println(message);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(message);
		if (jsonObject.containsKey("packet")) {
			switch (jsonObject.get("packet").toString()) {
			case "login":
				String username = jsonObject.get("username").toString();
				String password = jsonObject.get("password").toString();
				session.getBasicRemote().sendText("Connecting to server...");
				LoginManager.login(session, username, password);
				break;
			case "fish":
				//this kind of stuff definitely needs to be fixed!
				for(Player p : GameEngine.players) {
					if(p.session.equals(session))
						Fishing.catchFish(p);
				}
				break;
			}
		}
	}


	@OnClose
	public void onWebSocketClose(CloseReason reason, Session session) throws SQLException {
		println(session + " closed " + reason);
		//this is terrible code as it has to go through the whole player list
		for(Player p : GameEngine.players) {
			if(session.equals(p.session)) {
				WebServer.database.save(p);
				GameEngine.players.remove(p);
				//close all of the player tasks
			}
		}
	}


	@OnError
	public void onWebSocketError(Throwable cause) {
		System.out.println("onWebSocketError: ");
		cause.printStackTrace(System.err);
	}
}
