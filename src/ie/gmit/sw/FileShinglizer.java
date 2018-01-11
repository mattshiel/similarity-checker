package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;

public class FileShinglizer implements Shingleator {
	// List of words to shingle
	private List<String> words = new ArrayList<String>();
	// List of hashed shingles
	private List<Shingle> shingles = new ArrayList<Shingle>();
	
	// Shingle size
	private final int SHINGLE_SIZE = 3;
	private int docID;

	public FileShinglizer(List<String> words, int docID) {
		this.words = words;
		this.docID = docID;
	}

	@Override
	public List<Shingle> shingleize() {
		// Set a temp string to null
		String shingle = "";
		// Counts words added to the shingle
		int count = 0;
		
		// Loop over the array of words
		for (int i = 0; i < words.size(); i++) {
			// Add words to the shingle with concatenation (could switch to StringBuilder)
			shingle += words.get(i);
			count++;
			
			// Add the shingle once it is created and reset
			if (count == SHINGLE_SIZE) {
				shingles.add(new Shingle(shingle.hashCode(), docID));
				shingle = "";
				count = 0;
			}
			
			// If there are any words left over then add them as shingles regardless
			if (count < SHINGLE_SIZE && i == words.size() - 1) {
				shingles.add(new Shingle(shingle.hashCode(), docID));
			}
		}
		
		// Return the shingles
		return shingles;
	}
}
