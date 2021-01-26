package net.game;

public class Util {
	
	public static String formatPlayerName(String str) {
		str = ucFirst(str);
		str.replace("_", " ");
		return str;
	}
	
	public static String ucFirst(String str) {
		str = str.toLowerCase();
		if(str.length() > 1) {
			str = str.substring(0,1).toUpperCase() + str.substring(1);
		} else {
			return str.toUpperCase();
		}
		return str;
	}

}
