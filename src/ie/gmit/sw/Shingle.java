package ie.gmit.sw;

public class Shingle {
	private int shingleHashCode;
	private int docID;

	public Shingle(int shingleHashCode, int docID) {
		this.setShingleHashCode(shingleHashCode);
		this.docID = docID;
	}
	
	public int getDocID() {
		return docID;
	}

	public int getShingleHashCode() {
		return shingleHashCode;
	}

	public void setShingleHashCode(int shingleHashCode) {
		this.shingleHashCode = shingleHashCode;
	}
}
