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
	private Session session;
	
	public void setSession(Session s) {
		this.session = s;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void logout() throws SQLException, IOException {
		GameEngine.playerHandler.players.remove(this);
		save();
		session.close();
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
			session.getBasicRemote().sendText(data.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save() {
		data.put("lastIp", Connection.getAddress(session));
		try {
			WebServer.getAccountsDatabase().save(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
