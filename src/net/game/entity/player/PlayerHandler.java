package net.game.entity.player;

import java.sql.SQLException;
import java.util.LinkedList;

import net.game.WebServer;


public class PlayerHandler {
	
	public LinkedList<Player> players = new LinkedList<Player>();

	public void process() { 
		for(int i = 0; i < players.size(); i++) {
			players.get(i).process();
		}
	}
	
	public void saveAll() throws SQLException {
		for(Player p : players) {
			WebServer.getAccountsDatabase().save(p);
		}
	}

}
