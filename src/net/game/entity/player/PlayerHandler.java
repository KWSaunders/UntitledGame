package net.game.entity.player;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.json.simple.JSONObject;

public class PlayerHandler {
	
	public LinkedList<Player> players = new LinkedList<Player>();

	public void process() { 
		for(int i = 0; i < players.size(); i++) {
			players.get(i).process();
		}
	}
	
	/** Saves data for all players online */
	public void saveAll() throws SQLException {
		for(Player player : players) {
			player.save();
		}
	}
	
	public JSONObject createNewPlayer() {
		JSONObject json = new JSONObject();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		json.put("created", dtf.format(now));
		json.put("combatLevel", "1");
		json.put("privilege", "player"); //player, moderator, administrator
		json.put("currentXp", "0");
		json.put("gold", "0");
		json.put("timePlayed", "0");
		return json;
	}

}
