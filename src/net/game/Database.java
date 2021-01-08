package net.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.game.entity.player.Player;

public class Database {
	
	Connection connection = null;
	
	public Database(String host, String port, String db, String admin, String password) {
		try {
		Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
		connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/"+ db, admin, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("MySQL database connected!");
		if(password.isEmpty()) {
			System.out.println("Change MySQL Database password to non-empty password for live version.");
		}
	}
	
	public String lookup(String username) throws SQLException, ParseException {
		System.out.println("Looking up username: " + username);
		java.sql.Statement statement = connection.createStatement(); 
		ResultSet resultSet = statement.executeQuery("select * from player where username = '" + username + "'");
		while(resultSet.next())  {
			return resultSet.getString(1);
		}
		return null;
	}
	
	
	public String getPassword(String username) throws SQLException, ParseException {
		System.out.println("Looking up password for " + username);
		java.sql.Statement statement = connection.createStatement(); 
		ResultSet resultSet = statement.executeQuery("select * from player where username = '" + username + "'");
		while(resultSet.next())  {
			return resultSet.getString(2);
		}
		return null;
	}
	
	public JSONObject loadPlayerData(Player p) throws SQLException, ParseException {
		System.out.println("Loading account for " + p.username);
		java.sql.Statement statement = connection.createStatement(); 
		ResultSet resultSet = statement.executeQuery("select * from player where username = '" + p.username + "'");
		JSONObject jsonObject = null;
		while(resultSet.next())  {
			String json = resultSet.getString(3);
			if(json == null) {
				return null;
			}
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(json);
			System.out.println("Loaded: " + jsonObject.toJSONString());
		}
		return jsonObject;
	}
	
	public void save(Player p) throws SQLException {
	      String query = "update player set data = ? where username = ?";
	      PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString(1, p.data.toJSONString());
	      preparedStmt.setString(2, p.username);
	      preparedStmt.executeUpdate();
	      System.out.println("Finished saving... " + p.username);
	}
	
	public void viewAll() throws SQLException {
		System.out.println("Accessing database...");
		java.sql.Statement statement = connection.createStatement(); 
		
		String table = "player";
		ResultSet resultSet = statement.executeQuery("select * from " + table);  
		
		//ResultSet resultSet = statement.executeQuery("select * from player where username = 'xyle'");
		while(resultSet.next())  {
		try {
			String username = resultSet.getString(1);
			String password = resultSet.getString(2);
			String json = resultSet.getString(3);
			if(json == null) {
				System.out.println(username + ", " + password);
				return;
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			System.out.println(username + ", " + password + ", " + jsonObject.toJSONString());   
			} catch(Exception e) {
				System.out.println(e);
			}  
		}  
	}
	
	//create new account
	public void register(String user, String pass, JSONObject data) throws SQLException, ParseException{
		System.out.println("Creating new account for " + user);
		//java.sql.Statement statement = connection.createStatement();
		
		// the mysql insert statement
	      String query = " insert into player (username, password, data)"
	        + " values (?, ?, ?)";

	      // create the mysql insert prepared statement
	      PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString(1, user);
	      preparedStmt.setString(2, pass);
	      preparedStmt.setString(3, data.toJSONString());		//left as null for right now. Errors with JSONParser if I didnt
	    //or data.toString() not sure yet (the inverse of parse)
	      
	      // execute the prepared statement
	      preparedStmt.execute();
		
//	}
	}

}
