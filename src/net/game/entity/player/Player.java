package net.game.entity.player;

import javax.websocket.Session;

import org.json.simple.JSONObject;

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

}
