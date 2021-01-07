package net.game;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.simple.parser.ParseException;

import net.game.entity.player.Player;
public class AccountCreation {
	
	public static void create(Session session, String input1, String input2) throws SQLException, ParseException, IOException {
		//simple method for creating accounts
		// will want to add restrictions for names, passwords and their lengths
		
		session.getBasicRemote().sendText("Creating account....");
		WebServer.database.newPlayer(input1, input2);
		Player p = new Player();
		p.username = input1;
		p.password = input2;
		p.data = WebServer.database.load(p);
		GameEngine.players.add(p);
		p.session = session;
		session.getBasicRemote().sendText("Account Created!");
	}

}
