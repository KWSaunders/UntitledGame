package net.game;

import java.sql.SQLException;

import net.game.entity.player.*;
import net.util.Task;
import net.util.TaskScheduler;

public class GameEngine extends Thread {
	
	/**
	 * The task scheduler.
	 */
	private static final TaskScheduler scheduler = new TaskScheduler();

	/**
	 * Gets the task scheduler.
	 * @return The task scheduler.
	 */
	public static TaskScheduler getTaskScheduler() {
		return scheduler;
	}
	
	//5 minutes
	final int SAVE_CYCLE = 5 * 60;
	
	
	public static PlayerHandler playerHandler = new PlayerHandler();
	
	public static int getPlayersOnline() {
		return GameEngine.playerHandler.players.size();
	}
	
	@Override
	public void run() {
		System.out.println("Game Engine Started!");
		scheduler.schedule(new Task() {
			long ticks = 0;
			@Override
			protected void execute() {
				playerHandler.process();
				if(ticks % SAVE_CYCLE == 0 && ticks >= SAVE_CYCLE) {
					try {
						playerHandler.saveAll();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ticks++;
//				npcHandler.process();
//				itemHandler.process();
//				shopHandler.process();
			}
		});
	}

}
