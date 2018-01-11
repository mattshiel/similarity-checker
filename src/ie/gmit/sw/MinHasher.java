package ie.gmit.sw;

import java.util.Random;

public class MinHasher {

	// Constants
	private static final long SEED = 40;
	private static final int K = 6;
	
	// Member variables
	private static int[] minhashes = new int[K];
	
	public static int[] hash() {
		Random r = new Random(SEED);
		// Generate K number of hashes to be added to the set
		for (int i = 0; i < K; i++) {
			// Each hash is the next integer in the random sequence.
			minhashes[i] = r.nextInt();
		}
		return minhashes;
	}
}
