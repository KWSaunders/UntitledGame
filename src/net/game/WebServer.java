package net.game;

import java.io.*;
import java.sql.*;

import javax.websocket.server.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.websocket.jsr356.server.deploy.*;

public class WebServer {
	
	/** Credentials for MySql Database connection */
	final static String DB_ADDRESS = "localhost";
	final static String DB_PORT = "3306";
	final static String DB_DIRECTORY = "accounts";
	final static String DB_ADMIN  = "root";
	final static String DB_PASSWORD = "";
	
	static Database database = new Database(DB_ADDRESS, DB_PORT, DB_DIRECTORY, DB_ADMIN, DB_PASSWORD);

	public static void main(String[] args) throws SQLException, InterruptedException, IOException {
		GameEngine game = new GameEngine();
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.addConnector(connector);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.setStopTimeout(10000);
		server.setHandler(context);
		server.setStopTimeout(10000);
		try {
			ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
			wscontainer.addEndpoint(Connection.class);
			database.viewAll();
			if(DB_PASSWORD.isEmpty()) {
				System.out.println("Change MySQL DB password to non-empty password for live version.");
			}
			game.start();
			server.start();
			server.join();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}

}
