package net.game.entity.player.skills;

import net.game.GameEngine;
import net.game.entity.player.Player;
import net.util.Task;

public class Fishing {
	
	public static void catchFish(Player p) {
		GameEngine.getTaskScheduler().schedule(new Task(2, true) {
			double chance = Math.random();
			private int count = 3;
			@Override
			protected void execute() {
				if (count > 2) {
					//p.sendMessage("You attempt to catch some fish");
				} else if(count == 1){
					String success = "You catch some shrimps";
					String fail = "You fail to catch anything";
					String msg = chance > 0.8 ? success : fail;
					System.out.println(chance);
					System.out.println(msg);
						//add 10 xp
					if(msg.equals(success)) {
						String value = p.data.get("fishing").toString();
						int currentXp = Integer.parseInt(value);
						p.data.put("fishing", currentXp + 10);
						String value2 = p.data.get("shrimp").toString();
						int shrimp = Integer.parseInt(value2);
						p.data.put("shrimp", shrimp + 1);
					}
					//p.sendMessage(msg);
					stop();
				}
				count--;
			}
		});
	}

}
