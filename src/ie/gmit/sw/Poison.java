package ie.gmit.sw;

public class Poison extends Shingle {

	public Poison(int shingleHashCode, int docID) {
		super(shingleHashCode, docID);
		this.setShingleHashCode(-99);
	}

}