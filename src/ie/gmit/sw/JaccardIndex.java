package ie.gmit.sw;

public class JaccardIndex {
	/*
	 *  Member Variables
	 */
	private final int intersection;
	private final int set1Size;
	private final int set2Size;
	private float jaccard;

	public JaccardIndex(int intersection, int itemASize, int itemBSize) {
		this.intersection = intersection;
		this.set1Size = itemASize;
		this.set2Size = itemBSize;
	}

	/*
	 *  Calculate the Jaccard Index
	 */
	public String calculateIndex() {
		// Calculate the index
		jaccard = (float) intersection / (((float) set1Size + (float) set2Size) - (float) intersection);
		// Convert result to a string and to two decimal places
		String jaccardPercentage = String.format("%.2f", jaccard * 100);
		// Output result
		return "Document Similarity:" + jaccardPercentage + "%\n"; 
	}

	// Getters and Setters
	public int getIntersection() {
		return intersection;
	}

	public int getSet1Size() {
		return set1Size;
	}

	public int getSet2Size() {
		return set2Size;
	}
}