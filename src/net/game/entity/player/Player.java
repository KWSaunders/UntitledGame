package net.game.entity.player;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.simple.JSONObject;

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
		WebServer.getAccountsDatabase().save(this);
		session.close();
		System.out.println(this.username + " has disconnected.");
	}

	public void process() {
		try {
			session.getBasicRemote().sendText(data.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
