package ie.gmit.sw;

public class JaccardIndex {
	private final int intersection;
	private final int set1Size;
	private final int set2Size;
	private float jaccard;

	public JaccardIndex(int intersection, int itemASize, int itemBSize) {
		this.intersection = intersection;
		this.set1Size = itemASize;
		this.set2Size = itemBSize;
	}

	public String calculateIndex() {
		jaccard = (float) intersection / (((float) set1Size + (float) set2Size) - (float) intersection);
		String jaccardPercentage = String.format("%.2f", jaccard * 100);
		return "Document similarity:" + jaccardPercentage + "%"; 
	}

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