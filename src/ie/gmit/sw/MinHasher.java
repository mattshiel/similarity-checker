package ie.gmit.sw;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MinHasher {

	// Constants
	private static final long SEED = 50;
	private static final int K = 500;
	
	// Member variables
	private static Set<Integer> minhashes = new TreeSet<Integer>();

	public static Set<Integer> hash() {
		Random r = new Random(SEED);
		// Generate K number of hashes to be added to the set
		for (int i = 0; i < K; i++) {
			// Each hash is the next integer in the random sequence.
			minhashes.add(r.nextInt());
		}
		return minhashes;
	}
}
