package net.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

	
	public static ArrayList<String> loadFile(String path) {
		ArrayList<String> file = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				file.add(scanner.nextLine());
				//System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Finished reading " + path);
		return file;
	}

}
