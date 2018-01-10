package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileParser implements Parserator {

	@SuppressWarnings("unused")
	private File file;
	private BufferedReader br;
	private String line = "";
	// ArrayList of strings that contain the words from the text file
	private List<String> words = new ArrayList<String>();
	
	@Override
	public List<String> parse(String location) {		
		
		try {
			this.file = new File(location);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(location)));
			
			try {
				// If the user enters this block the file has been read successfully
				System.out.println("File " + file.getName() +  " read successfully!");
				
				while((line = br.readLine()) != null) {
					String[] split = line.split(" ");
					// Add split words to the array list
					for (int i = 0; i < split.length; i++) {
						words.add(split[i]);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// Main menu will loop and this error will inform the user to try again
			System.out.println("Error file " + file.getName() + " not found, please try again.");
		}
		return words;
	}

}
