package net.game.entity.player;

import java.io.IOException;
import java.sql.SQLException;

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
	
	public void process() {
		increment("timePlayed", 1);
		update();
	}
	
	public void update() {
		JSONObject json = data;
		json.put("packet", "update");
		try {
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
			System.out.println("Exception while saving to database!");
			e.printStackTrace();
		}
	}
	
	public void logout() throws SQLException, IOException {
		set("lastIp", Connection.getAddress(session));
		save();
		session.close();
		GameEngine.getPlayers().remove(this);
		System.out.println(this.username + " has disconnected.");
	}
	
	void set(String key, String val) {
		data.put(key, val);
	}
	
	void increment(String name, long amount) {
		String item = (String) data.get(name);
		long current = Integer.parseInt(item);
		long adjustment = current + amount;
		set(name, adjustment + "");
	}
	
	void decrement(String name, long amount) {
		String item = (String) data.get(name);
		long current = Integer.parseInt(item);
		long adjustment = current - amount;
		set(name, adjustment + "");
	}

}
