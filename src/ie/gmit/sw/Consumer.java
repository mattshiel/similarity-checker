package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

	/*
	 * Member Variables
	 */
	// BlockingQueue of Shingles
	private BlockingQueue<Shingle> queue;
	// Tree set to hold minhashes
	private Set<Integer> minhashes;
	// A concurrent hashmap to hold a document ID and its minhashes
	private Map<Integer, List<Integer>> map = new ConcurrentHashMap<Integer, List<Integer>>(2, 0.75f);
	// Threadpool initialized to the number of hashes
	private ExecutorService pool = Executors.newFixedThreadPool(10);
	
	//Default constructor
	public Consumer() {
		
	}
	
	public Consumer(BlockingQueue<Shingle> queue) {
		this.queue = queue;
		init();
	}
	
	// Generate minhashes on startup
	public void init() {
		minhashes = MinHasher.hash();
	}
	
	public synchronized int computeHash(Shingle s) {
		// Default maximum to the minimum first
		int min = Integer.MAX_VALUE;
		
		for (Integer hash : minhashes) {
			int minHash = s.getShingleHashCode() ^ hash;
			// If the result is smaller than max value min becomes the greatest value
			// until the smallest possible minhash is achieved
			if (minHash < min) {
				min = minHash;
			}
		}
		return min;
	}
	
	@Override
	public void run() {
		// ArrayLists for holding each documents minhashes
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		
		// while loop control variable
		int docCount = 2;
		
		System.out.println("Calculating similarity...");
		
		while (docCount != 0) {
			try {
				// Take a shingle off the end of the queue
				Shingle shingle = queue.take();
				
				// If you reach the EOF/Poison shingle
				if (shingle instanceof Poison) {
					docCount--;
				} else {
					// Create a new worker thread
					pool.execute(new Runnable() {
	
						@Override
						public void run() {
							// Depending on which file, calculate and add minhashes
							if (shingle.getDocID() == 1) {
								list1.add(computeHash(shingle));
							} else if (shingle.getDocID() == 2) {
								list2.add(computeHash(shingle));
							}
						}	
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Shutdown the threadpool safely
		shutdownPool();
		
		// Store the size of the lists for Jaccard calculation
		int k1 = list1.size();
		int k2 = list2.size();

		// Add both lists to the map
		map.put(1, list1);
		map.put(2, list2);

		// Store the intersecting values
		List<Integer> intersection = map.get(1);
		intersection.retainAll(map.get(2));
		
		// New instance of Jaccard Index
		JaccardIndex jaccard = new JaccardIndex(intersection.size(), k1, k2);
		// Calculate Jaccard
		String result = jaccard.calculateIndex();
		// Output no. of comparisons and similarity percentage 
		System.out.println("\nComparisons Made: " + jaccard.getIntersection() + "\n" + result);
	}

	void shutdownPool() {
		pool.shutdown(); 
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			System.out.println("failure shutting down threa pool");
			e.printStackTrace();
		}
	}
}

