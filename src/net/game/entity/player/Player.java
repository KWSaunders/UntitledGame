package net.game.entity.player;

import java.io.IOException;

import javax.websocket.Session;

import org.json.simple.JSONObject;

public class Player {
	
	public String username;
	public String password;
	public JSONObject data;
	public Session session;
	
	public void sendMessage(String msg) {
		try {
			this.session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * HashMap<String,Object> additionalDetails = new HashMap<String,Object>();
additionalDetails.put("showOppo", option.isShowOppo());
additionalDetails.put("showCont", option.isShowCont());
additionalDetails.put("contActionTaken", option.isConActionTaken());
additionalDetails.put("oppoActionTaken", option.isOppoActionTaken());

JSONObject additionalDetailsJSON = new JSONObject(additionalDetails);
	 */

}
