package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileParser implements Runnable{

	/*
	 * Member variables
	 */
	// FileParser parametres
	private File file;
	private int docID;
	BlockingQueue<Shingle> queue = new LinkedBlockingQueue<Shingle>(10000);
	
	// Buffered reader for fast file parsing
	private BufferedReader br;
	// Control for the while loop while parsing
	private String line = "";
	
	// Array List to contain words in document
	private List<String> words = new ArrayList<String>();
	// Array list to contain shingles
	private List<Shingle> shingles = new ArrayList<Shingle>();
	
	// Shingleator to convert words to shingles
	private Shingleator shingleizer;	
	
	/*
	 *  Default Constructor
	 */
	public FileParser() {
		
	}
	
	public FileParser(String location, int docID, BlockingQueue<Shingle> queue) {
		this.file = new File(location);
		this.docID = docID;
		this.queue = queue;
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
		
		// Instantiate shinglizer as a FileShingelizer
		shingleizer = new FileShingleizer(words, docID);
		// Shingleize file and store shingles in list
		shingles = shingleizer.shingleize();
				
		// Add each hashed shingle to the blocking queue
		for(Shingle shingle : shingles) {
	          queue.add(shingle);
	          System.out.println(shingle);
		}
	}
}
