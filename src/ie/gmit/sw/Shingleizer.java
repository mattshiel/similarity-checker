package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;

public class Shingleizer implements Shingleator {
	// Shingled list of words
	private List<String> shingles = new ArrayList<String>();
	// Shingle size
	private final int SHINGLE_SIZE = 2;

	public Shingleizer() {
	}

	@Override
	public List<String> shingleize(List<String> words) {
		// Set a temp string to null
		String shingle = "";
		// Counts words added to the shingle
		int count = 0;
		
		// Loop over the array of words
		for (int i = 0; i < words.size(); i++) {
			// Add words to the shingle with concatenation (might switch to StringBuilder)
			shingle += words.get(i);
			count++;
			// If a shingle is made, reset the template
			if (count == SHINGLE_SIZE) {
				shingles.add(shingle);
				shingle = "";
				count = 0;
			}
			
			// If there are any words left over then add them as shingles regardless
			if (count < SHINGLE_SIZE && i == words.size() - 1) {
				shingles.add(shingle);
			}
		}
		
		// Return the shingles
		return shingles;
	}
}
