package net.game.entity.player;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.websocket.Session;

import org.json.simple.JSONObject;

import net.game.Connection;
import net.game.GameEngine;
import net.game.WebServer;
import net.game.entity.Entity;

public class Player extends Entity {
	
	public String username;
	public String password;
	public JSONObject data;
	public Session session;
	
	public Session getSession() {
		return session;
	}
	
	public void logout() throws SQLException, IOException {
		data.put("lastIp", Connection.getAddress(session));
		this.save();
		session.close();
		GameEngine.playerHandler.players.remove(this);
		System.out.println(this.username + " has disconnected.");
	}
	
	void increment(String name, long amount) {
		String item = (String) data.get(name);
		long current = Integer.parseInt(item);
		long adjustment = current + amount;
		data.put(name, adjustment + "");
	}
	
	void decrement(String name, long amount) {
		String item = (String) data.get(name);
		long current = Integer.parseInt(item);
		long adjustment = current - amount;
		data.put(name, adjustment + "");
	}

	public void process() {
		try {
			increment("timePlayed", 1);
			JSONObject json = data;
			json.put("packet", "updatePlayer");
			json.put("playersOnline", GameEngine.getPlayersOnline() + "");
			session.getBasicRemote().sendText(json.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			WebServer.getAccountsDatabase().save(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
