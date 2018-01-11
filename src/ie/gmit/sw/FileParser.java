package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileParser implements Runnable{

	private File file;
	private BufferedReader br;
	private String line = "";
	private Shingleator shingleMaker = new Shingleizer();
	// ArrayList of strings that contain the words from the text file
	private List<String> words = new ArrayList<String>();
	private List<String> shingles = new ArrayList<String>();
	
	public FileParser(String location) {
		this.file = new File(location);
	}
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			try {
				// If the user enters this block the file has been read successfully
				System.out.println("File " + file.getName() +  " read successfully!");
				
				while((line = br.readLine()) != null) {
					// This regex allows retention of special unicode characters but removes all other punctuation
					// Adapted from https://stackoverflow.com/questions/18830813/how-can-i-remove-punctuation-from-input-text-in-java
					String[] split = line.replaceAll("[^\\p{L} ]", "").toLowerCase().split("\\s+");
					// Add split words to the array list
					for (int i = 0; i < split.length; i++) {
						words.add(split[i].toLowerCase());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// Main menu will loop and this error will inform the user to try again
			System.out.println("Error file " + file.getName() + " not found, please try again.");
		}
		
		// Create shingles of fixed size 2 for every words
		shingles = shingleMaker.shingleize(words);
		
		// Hashes the shingles and adds to the blocking queue
		
		
		for(String s : shingles) {
	          System.out.println(s);
		}
	}
	
}
