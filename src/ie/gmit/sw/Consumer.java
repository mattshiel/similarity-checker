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
	private BlockingQueue<Shingle> queue;
	private Set<Integer> minhashes;
	//private int[] minhashes;
	private Map<Integer, List<Integer>> map = new ConcurrentHashMap<Integer, List<Integer>>();
	
	//int N_CPUS = Runtime.getRuntime().availableProcessors();
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
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		int docCount = 2;
		
		while (docCount > 0) {
			try {
				//Thread.sleep(10);
				Shingle shingle = queue.take();
				System.out.println("Shingle hashcode is: " + shingle.getShingleHashCode() + " DocID is: " + shingle.getDocID());
				
				// If you reach the EOF
				if(shingle.getShingleHashCode() != 0) {
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
				} else {
					docCount --;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		shutdownAndAwaitTermination(pool);

		// Store the size of the lists for Jaccard calculation
		int k1 = list1.size();
		int k2 = list2.size();

		// Add both lists to the map
		map.put(1, list1);
		map.put(2, list2);

		// Store the intersecting values
		List<Integer> intersection = map.get(1);
		intersection.retainAll(map.get(2));
		
		// New instance of Jaccard Index, and calculate
		JaccardIndex jaccard = new JaccardIndex(intersection.size(), k1, k2);
		float result = jaccard.calculateIndex();
		System.out.printf("Document1 " + jaccard.getSet1Size() + " shingles" + "\nDocument2 " + jaccard.getSet2Size() + " shingles" + "\nComparisons  : " + jaccard.getIntersection()
				+ "\nJaccard Index: %.2f",  result);
	}

	/**
	 * Safely shut down the ExecutorService. This will ensure that there are no
	 * new task submitted and will wait for at most 60 seconds to finish all
	 * tasks.
	 * 
	 * This method was adapted from
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html</a>
	 *  
	 */
	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
}
