package net.game;

import java.util.*;

import net.game.entity.player.*;
import net.util.Task;
import net.util.TaskScheduler;

public class GameEngine extends Thread {

	final int TICK_RATE = 600;
	static LinkedList<Player> players = new LinkedList<Player>();
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

	public static int getPlayerCount() {
		return players.size();
	}
	
	@Override
	public void run() {
		scheduler.schedule(new Task() {
			@Override
			protected void execute() {
//				playerHandler.process();
//				npcHandler.process();
//				itemHandler.process();
//				shopHandler.process();
			}
		});
	}

}
